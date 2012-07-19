package br.com.silvanopessoa.dao;

import java.util.List;

import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Accesslist;

public interface IAccesslistDAO {
	
	/**
	 * @return
	 */
	public List<Accesslist> findAll();

	/**
	 * @param entity
	 */
	public void delete(Accesslist entity);
	
	/**
	 * @param id
	 * @return
	 */
	public Accesslist findById(int id);

	/**
	 * @param entity
	 * @return
	 * @throws BusinessMessageException
	 */
	public List<Accesslist> find(Accesslist entity);
	
	/**
	 * @param entity
	 */
	void save(Accesslist entity);

	/**
	 * @param entity
	 */
	void update(Accesslist entity);
	
	/**
	 * @param entity
	 * @return
	 */
	public boolean hasDuplicidade(Accesslist entity);
}
