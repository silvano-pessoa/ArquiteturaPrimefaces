package br.com.silvanopessoa.dao;

import java.util.List;

import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Checkpoint;

public interface ICheckpointDAO {
	
	/**
	 * @return
	 */
	public List<Checkpoint> findAll();

	/**
	 * @param entity
	 */
	public void delete(Checkpoint entity);
	
	/**
	 * @param id
	 * @return
	 */
	public Checkpoint findById(int id);
	
	/**
	 * @param entity
	 * @return
	 * @throws BusinessMessageException
	 */
	public List<Checkpoint> find(Checkpoint entity);

	/**
	 * @param entity
	 */
	void save(Checkpoint entity);

	/**
	 * @param entity
	 */
	void update(Checkpoint entity);
	
	/**
	 * @param entity
	 * @return
	 */
	public boolean hasDuplicidade(Checkpoint entity);
	
	/**
	 * @param entity
	 * @return
	 */
	public List<Checkpoint> findbyAccesslist(Accesslist entity);
}
