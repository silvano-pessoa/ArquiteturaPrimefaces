/**
 * 
 */
package br.com.silvanopessoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.silvanopessoa.dao.ICheckpointDAO;
import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Checkpoint;
import br.com.silvanopessoa.service.ICheckpointService;
import br.com.silvanopessoa.support.ServiceSupport;

/**
 * @author silvano.pessoa
 *
 */
@Service
public class CheckpointService extends ServiceSupport implements ICheckpointService {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	private static final long serialVersionUID = -7676073575849665613L;
	
	@Resource
	ICheckpointDAO dao;
	
	private final int LIMITE_MENSAGEM=3;
	
	/**************************************************************/
	/****************** PERSISTÊNCIA DE DADOS *********************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#save(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Checkpoint entity) throws BusinessMessageException,ApplicationMessageException{
		if( entity.getChkId() == 0 ){
			this.internalSave(entity);
		}else{
			this.update(entity);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#internalSave(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void internalSave(Checkpoint entity) throws BusinessMessageException,ApplicationMessageException{
		try {
			this.validateCheckpoint(entity);
			dao.save(entity);
		}catch (BusinessMessageException e) {
			throw e;
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#update(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void update(Checkpoint entity) throws BusinessMessageException,ApplicationMessageException{
		try {
			this.validateCheckpoint(entity);
			dao.update(entity);
		}catch (BusinessMessageException e) {
			throw e;
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#delete(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Checkpoint entity) throws BusinessMessageException,ApplicationMessageException{
		try {
			// RN01 - Verificar se existe checkpoints vinculados a perfil.
			this.validateRemove(entity);
			
			//RN02 - Se nao existir remove.								 
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
	 * @see br.com.silvanopessoa.service.ICheckpointService#findAll()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Checkpoint> findAll() throws ApplicationMessageException {
		try {
			return dao.findAll();
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#findById(int)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Checkpoint findById(int id) throws ApplicationMessageException{
		try {
			return dao.findById(id);
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}		
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#find(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Checkpoint> find(Checkpoint entity) throws ApplicationMessageException{
		try {
			return dao.find(entity);
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#findByNome(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Checkpoint> findByNome(String nome) throws ApplicationMessageException{
		Checkpoint pesquisa	= new Checkpoint();
		pesquisa.setNome(nome);
		
		try {
			return this.find(pesquisa);
		} catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#findbyAccesslist(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Checkpoint> findbyAccesslist(Accesslist entity) throws ApplicationMessageException{
		try {
			return dao.findbyAccesslist(entity);
		}catch (Exception e) {
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
	private void validateCheckpoint(Checkpoint entity) throws BusinessMessageException{
		if(dao.hasDuplicidade(entity)){
			throw new BusinessMessageException(this.getMessage("exception.checkpoint.nome.cadastrado", new Object [] {entity.getNome()}));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#validateCheckpointRemove(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	private void validateRemove(Checkpoint entity) throws BusinessMessageException{
		Checkpoint entidade	= dao.findById(entity.getChkId());
		
		String pendencias = new String();
		
		pendencias +=this.validateAccesslist(entidade);
		
		if(pendencias.length()>0){
			throw new BusinessMessageException(this.getMessage("exception.generic.com.fk", new Object [] {pendencias}));
		}
	}	
	
	/**
	 * CHECKPOINT_EXCLUIR - RN01 - Verificar se existe checkpoints vinculados ao perfil.
	 * 
	 * @param pendencias
	 * @param entity
	 * @return
	 * @throws BusinessMessageException 
	 */
	private String validateAccesslist(Checkpoint entity) throws BusinessMessageException{
		String pendencias	= "";
		int mensagem =0;
		
		// Verifica se existe Accesslist.
		if(entity.getAccesslists()!=null && entity.getAccesslists().size()>0){
			pendencias= pendencias+"<br/>Perfil:<br/>";
			for (Accesslist access : entity.getAccesslists()) {
				pendencias=pendencias+"- Nome: "+access.getNome()+"<br/>";
				mensagem++;
				if(mensagem==LIMITE_MENSAGEM) break;
			}
		}
		
		return pendencias;
	}	
}