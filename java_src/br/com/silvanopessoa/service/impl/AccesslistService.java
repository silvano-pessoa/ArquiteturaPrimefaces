/**
 * 
 */
package br.com.silvanopessoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.silvanopessoa.dao.IAccesslistDAO;
import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Checkpoint;
import br.com.silvanopessoa.model.entity.Menu;
import br.com.silvanopessoa.service.IAccesslistService;
import br.com.silvanopessoa.service.ICheckpointService;
import br.com.silvanopessoa.support.ServiceSupport;

/**
 * @author silvano.pessoa
 *
 */
@Service
public class AccesslistService extends ServiceSupport implements IAccesslistService {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	private static final long serialVersionUID = -7676073575849665613L;
	
	@Resource
	IAccesslistDAO dao;
	
	@Resource
	ICheckpointService serviceCheckpoint;
	
	private final int LIMITE_MENSAGEM=3;
	
	/**************************************************************/
	/****************** PERSISTÊNCIA DE DADOS *********************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#save(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Accesslist entity) throws BusinessMessageException,ApplicationMessageException{
		if( entity.getAclId() == 0 ){
			this.internalSave(entity);
		}else{
			this.update(entity);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#internalSave(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void internalSave(Accesslist entity) throws BusinessMessageException,ApplicationMessageException{
		try {
			this.validateAccesslist(entity);
			dao.save(entity);

			// Salvar Perfil
			if( entity.getCheckpoints() != null ){
				for(Checkpoint checkpoint:entity.getCheckpoints()){
					if(checkpoint.getChkId()!=0){
						Checkpoint obj	= serviceCheckpoint.findById(checkpoint.getChkId());
						obj.getAccesslists().add(entity);
					}					
				}				
			}
			
		}catch (BusinessMessageException e) {
			throw e;			
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#update(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void update(Accesslist entity) throws BusinessMessageException,ApplicationMessageException{
		try {
			this.validateAccesslist(entity);
			dao.update(entity);

			// Remover Perfil
			if( entity.getCheckpointsDelete() != null ){
				for(Checkpoint checkpoint:entity.getCheckpointsDelete()){
					if(checkpoint.getChkId()!=0){
						Checkpoint obj	= serviceCheckpoint.findById(checkpoint.getChkId());
						obj.getAccesslists().remove(entity);						
					}
				}				
			}
			
			if( entity.getCheckpoints() != null ){
				for(Checkpoint checkpoint:entity.getCheckpoints()){
					if(checkpoint.getChkId()!=0){
						Checkpoint obj	= serviceCheckpoint.findById(checkpoint.getChkId());
						obj.getAccesslists().remove(entity);						
					}
				}				
			}
			
			// Salvar Perfil
			if( entity.getCheckpoints() != null ){
				for(Checkpoint checkpoint:entity.getCheckpoints()){
					if(checkpoint.getChkId()!=0){
						Checkpoint obj	= serviceCheckpoint.findById(checkpoint.getChkId());
						obj.getAccesslists().add(entity);
					}					
				}				
			}
			
		}catch (BusinessMessageException e) {
			throw e;			
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#delete(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Accesslist entity) throws ApplicationMessageException, BusinessMessageException{
		
		try {
			// RN01 - Verificar se existe vinculados ao perfil.
			this.validateRemove(entity);
			
			//RN02 - Se nao existir remove.			 
			 dao.delete(entity);

		}catch (BusinessMessageException e) {
			throw e;
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/**************************************************************/
	/************************ CONSULTAS ***************************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#findAll()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Accesslist> findAll() throws ApplicationMessageException {
		try {
			return dao.findAll();
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#findById(int)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Accesslist findById(int id) throws ApplicationMessageException{
		try {
			Accesslist obj	= dao.findById(id);
			obj.getCheckpoints();
			
			return obj;
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}		
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#find(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Accesslist> find(Accesslist entity) throws ApplicationMessageException{
		try {
			return dao.find(entity);
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IAccesslistService#findByNome(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Accesslist> findByNome(String nome) throws ApplicationMessageException{
		Accesslist pesquisa	= new Accesslist();
		pesquisa.setNome(nome);
		
		try {
			return this.find(pesquisa);
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
	private void validateAccesslist(Accesslist entity) throws BusinessMessageException{
		if(dao.hasDuplicidade(entity)){
			throw new BusinessMessageException(this.getMessage("exception.accesslist.nome.cadastrado", new Object [] {entity.getNome()}));
		}
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ICheckpointService#validateCheckpointRemove(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	private void validateRemove(Accesslist entity) throws BusinessMessageException{
		Accesslist entidade	= dao.findById(entity.getAclId());
		
		String pendencias = new String();
		
		pendencias +=this.validateCheckpoint(entidade);
		pendencias +=this.validateMenu(entidade);
		
		if(pendencias.length()>0){
			throw new BusinessMessageException(this.getMessage("exception.generic.com.fk", new Object [] {pendencias}));
		}
	}	
	
	/**
	 * PERFIL_EXCLUIR - RN01 - Verificar se existe perfil vinculados ao checkpoint.
	 * 
	 * @param pendencias
	 * @param entity
	 * @return
	 * @throws BusinessMessageException 
	 */
	private String validateCheckpoint(Accesslist entity) throws BusinessMessageException{
		String pendencias	= "";
		int mensagem =0;
		
		// Verifica se existe Accesslist.
		if(entity.getCheckpoints()!=null && entity.getCheckpoints().size()>0){
			pendencias= pendencias+"<br/>Checkpoint:<br/>";
			for (Checkpoint check : entity.getCheckpoints()) {
				pendencias=pendencias+"- Nome: "+check.getNome()+"<br/>";
				mensagem++;
				if(mensagem==LIMITE_MENSAGEM) break;
			}
		}
		
		return pendencias;
	}
	
	/**
	 * PERFIL_EXCLUIR - RN02 - Verificar se existe perfil vinculados ao menu.
	 * 
	 * @param pendencias
	 * @param entity
	 * @return
	 * @throws BusinessMessageException 
	 */
	private String validateMenu(Accesslist entity) throws BusinessMessageException{
		String pendencias	= "";
		int mensagem =0;
		
		// Verifica se existe Menu.
		if(entity.getMenus()!=null && entity.getMenus().size()>0){
			pendencias= pendencias+"<br/>Menu:<br/>";
			for (Menu menu : entity.getMenus()) {
				pendencias=pendencias+"- Nome: "+menu.getNomeInt()+"<br/>";
				mensagem++;
				if(mensagem==LIMITE_MENSAGEM) break;
			}
		}
		
		return pendencias;
	}
	
}