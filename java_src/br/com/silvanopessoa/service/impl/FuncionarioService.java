/**
 * 
 */
package br.com.silvanopessoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.silvanopessoa.dao.IFuncionarioDAO;
import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Funcionario;
import br.com.silvanopessoa.service.IFuncionarioService;
import br.com.silvanopessoa.support.ServiceSupport;
import br.com.silvanopessoa.util.SecurityUtils;
import br.com.silvanopessoa.validator.CpfValidator;

/**
 * @author silvano.pessoa
 *
 */

@Service
public class FuncionarioService extends ServiceSupport implements IFuncionarioService {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	private static final long serialVersionUID = -7676073575849665613L;
	
	@Resource
	IFuncionarioDAO dao;

	private final int LIMITE_MENSAGEM=3;
	
	/**************************************************************/
	/****************** PERSISTÊNCIA DE DADOS *********************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#save(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Funcionario entity) throws BusinessMessageException, ApplicationMessageException{
		if( entity.getFunId() == 0 ){
			this.internalSave(entity);
		}else{
			this.update(entity);
		}
	} 
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#internalSave(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@CacheEvict(value = "funcionarioCache", allEntries=true)
	private void internalSave(Funcionario entity) throws BusinessMessageException, ApplicationMessageException{
		try {
			// Validação CPF		
			if ( !CpfValidator.validaCPF( entity.getCpf() ) ) {
				throw new BusinessMessageException(this.getMessage("app.error.CPF"));
			}
			
			this.validateFuncionario(entity);
			
			entity.setSenha(SecurityUtils.md5(entity.getNovaSenha()));
			
			dao.save(entity);
		}catch (BusinessMessageException e) {
			throw e;
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#update(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@CachePut(value = "funcionarioCache")
	private void update(Funcionario entity) throws BusinessMessageException, ApplicationMessageException{
		try {
			// Validação CPF		
			if ( !CpfValidator.validaCPF( entity.getCpf() ) ) {
				throw new BusinessMessageException(this.getMessage("app.error.CPF"));
			}
					
			this.validateFuncionario(entity);
			this.validateAssociar(entity);
			
			Funcionario oldFunc	= this.findById(entity.getFunId());
			
			if( oldFunc.getAtivo() != entity.getAtivo() && !entity.getAtivo() ){
				this.validateDesativar(entity);
			}
			
			if(entity.getNovaSenha() != null && !entity.getNovaSenha().equals("")){
				entity.setSenha(SecurityUtils.md5(entity.getNovaSenha()));
			}
			
			dao.update(entity);
		}catch (BusinessMessageException e) {
			throw e;
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#delete(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@CacheEvict(value = "funcionarioCache", allEntries=true)
	public void delete(Funcionario entity) throws ApplicationMessageException, BusinessMessageException{
		try {
			//RN01 - Verificar se existe funcionarios vinculados ao boleto, reembolso ou funcionario superior.
			this.validateRemove(entity);
			dao.delete(entity);
			 
		}catch (BusinessMessageException e) {
			throw e;
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
		
	/**************************************************************/
	/************************ CONSULTAS ***************************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#findAll()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	@Cacheable(value="funcionarioCache")
	public List<Funcionario> findAll() throws ApplicationMessageException {
		try {
			return dao.findAll();
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}			
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Funcionario findById(int id) throws ApplicationMessageException{
		try {
			return dao.findById(id);
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}		
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#find(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Funcionario> find(Funcionario entity) throws ApplicationMessageException{
		try {
			return dao.find(entity);
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}	

	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#findByRazao(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Funcionario> findListByNome(String nome) throws ApplicationMessageException{
		Funcionario pesquisa	= new Funcionario();
		pesquisa.setNome(nome);
		
		try {
			return this.find(pesquisa);
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#findByRazao(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Funcionario getByUser(String user) throws ApplicationMessageException{
		try {
			return dao.getByUser(user);
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/**
	 * Retorna todos os funcionários ativos do sistema
	 * @return Lista com os funcionários ativos
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Funcionario> findByAtivos() throws ApplicationMessageException{
		Funcionario funcionario	= new Funcionario();
		funcionario.setAtivo(true);
		
		return this.find(funcionario);
	}
	
	/**s
	 * Retorna todos os funcionários que são subordinados ao superior
	 * @return Lista com os funcionários subordinados
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Funcionario> findBySuperior(Funcionario entity) throws ApplicationMessageException{
		try {
			return dao.findBySuperior(entity);
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#getByNome(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Funcionario findByNome(String nome) throws ApplicationMessageException{
		try {
			return dao.findByNome(nome);
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#findBySiteCliente(int)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Funcionario> findBySiteCliente(int cliId) throws ApplicationMessageException {
		return dao.findBySiteCliente(cliId);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#findBySite(int)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Funcionario> findBySite(int sitId)throws ApplicationMessageException {
		try {
			return dao.findBySite(sitId);
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/**************************************************************/
	/************************ VALIDAÇÕES **************************/
	/**************************************************************/

	/**
	 * @param entity
	 * @throws BusinessMessageException
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	private void validateFuncionario(Funcionario entity) throws BusinessMessageException{
		if(dao.hasDuplicidade(entity)){
			throw new BusinessMessageException(this.getMessage("exception.funcionario.userCPF.cadastrado", new Object [] {entity.getUser(), entity.getCpf()}));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IFuncionarioService#validateFuncionarioRemove(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	private void validateRemove(Funcionario entity) throws BusinessMessageException, ApplicationMessageException{
		Funcionario entidade	= this.findById(entity.getFunId());
		
		String pendencias = new String();
		
		pendencias +=this.validateFuncionarioSuperior(entidade);
		
		if(pendencias.length()>0){
			throw new BusinessMessageException(this.getMessage("exception.generic.com.fk", new Object [] {pendencias}));
		}
	}
	
	
	/**
	 * FUNCIONARIO_EXCLUIR - RN02 -Verifica se existe funcionario superior
	 * 
	 * @param pendencias
	 * @param entity
	 * @return
	 * @throws BusinessMessageException 
	 */
	private String validateFuncionarioSuperior(Funcionario entity) throws BusinessMessageException, ApplicationMessageException{
		String pendencias	= "";
		int mensagem		= 0;
		
		// RN02-Verifica se existe funcionario superior.
		List<Funcionario> list	= this.findBySuperior(entity);

		if(list.size()>0){
			pendencias= pendencias+"<br/>Funcionario Supervisor:<br/>";
			for (Funcionario funcionario : list) {
				pendencias=pendencias+"- "+funcionario.getNome()+"<br/>";
				mensagem++;
				if(mensagem==LIMITE_MENSAGEM) break;
			}
		}
		
		return pendencias;
	}
	
	/**
	 * FUNCIONARIO_DESATIVAR - RN01 - Não permitir que um funcionário seja superior de si mesmo
	 * 
	 * @param entity
	 * @throws BusinessMessageException
	 */
	public void validateAssociar(Funcionario entity) throws BusinessMessageException{
		if( entity.getSuperior()!=null && entity.getFunId() == entity.getSuperior().getFunId() ){
			throw new BusinessMessageException(this.getMessage("exception.funcionario.associar.cadastrado"));
		}
	}	
	
	/**
	 * FUNCIONARIO_ASSOCIAR - RN01 - Não permitir que um funcionário supervisor seja desativado 
	 * 								 até que não haja mais nenhuma associação a outros funcionários
	 * 
	 * @param entity
	 * @throws BusinessMessageException
	 * @throws ApplicationMessageException 
	 */
	public void validateDesativar(Funcionario entity) throws BusinessMessageException, ApplicationMessageException{
		List<Funcionario> listFuncionario	= this.findBySuperior(entity);
		
		if( listFuncionario.size() > 0 ){
			throw new BusinessMessageException(this.getMessage("exception.funcionario.desativar.cadastrado"));
		}
	}	
}
