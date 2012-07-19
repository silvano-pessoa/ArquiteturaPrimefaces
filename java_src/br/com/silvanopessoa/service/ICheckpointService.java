package br.com.silvanopessoa.service;
import java.util.List;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Checkpoint;

/**
 * @author silvano.pessoa
 *
 */
public interface ICheckpointService {
	
	
	/**
	 * @param entity
	 * @throws ApplicationMessageException
	 * @throws BusinessMessageException
	 */
	public void delete(Checkpoint entity) throws ApplicationMessageException, BusinessMessageException;
	
	/**
	 * @param entity
	 * @throws ApplicationMessageException
	 * @throws BusinessMessageException
	 */
	public void save(Checkpoint entity) throws ApplicationMessageException, BusinessMessageException;
	

	/**
	 * @param entity
	 * @return
	 * @throws BusinessMessageException
	 * @throws ApplicationMessageException
	 */
	public List<Checkpoint> find(Checkpoint entity) throws BusinessMessageException,ApplicationMessageException;
	
	/**
	 * @param id
	 * @return
	 */
	public Checkpoint findById(int id) throws ApplicationMessageException;

	/**
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Checkpoint> findAll() throws ApplicationMessageException;

	/**
	 * @param nome
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Checkpoint> findByNome(String nome) throws ApplicationMessageException;
	
	/**
	 * @param entity
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Checkpoint> findbyAccesslist(Accesslist entity) throws ApplicationMessageException;
}