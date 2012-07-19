package br.com.silvanopessoa.service;
import java.util.List;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Accesslist;

/**
 * @author silvano.pessoa
 *
 */
public interface IAccesslistService {
	
	
	/**
	 * @param entity
	 * @throws ApplicationMessageException
	 * @throws BusinessMessageException
	 */
	public void delete(Accesslist entity) throws ApplicationMessageException, BusinessMessageException;
	
	/**
	 * @param entity
	 * @throws ApplicationMessageException
	 * @throws BusinessMessageException
	 */
	public void save(Accesslist entity) throws ApplicationMessageException, BusinessMessageException;
	
	/**
	 * @param id
	 * @return
	 */
	public Accesslist findById(int id) throws ApplicationMessageException;

	/**
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Accesslist> findAll() throws ApplicationMessageException;
	
	/**
	 * @param nome
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Accesslist> findByNome(String nome) throws ApplicationMessageException;
	
	/**
	 * @param entity
	 * @return
	 * @throws BusinessMessageException
	 * @throws ApplicationMessageException
	 */
	public List<Accesslist> find(Accesslist entity) throws BusinessMessageException,ApplicationMessageException;
}