package br.com.silvanopessoa.dao;

import java.util.List;

import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Funcionario;

public interface IFuncionarioDAO {
	
	/**
	 * @param entity
	 */
	void save(Funcionario entity);

	/**
	 * @param entity
	 */
	void update(Funcionario entity);
	
	/**
	 * @param entity
	 */
	public void delete(Funcionario entity);	

	/**
	 * @return
	 */
	public List<Funcionario> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	public Funcionario findById(int id);
	
	/**
	 * @param entity
	 * @return
	 * @throws BusinessMessageException
	 */
	public List<Funcionario> find(Funcionario entity);

	/**
	 * @param entity
	 * @return
	 */
	public boolean hasDuplicidade(Funcionario entity);
	
	/**
	 * @param user
	 * @return
	 */
	public Funcionario getByUser(String user);

	/**
	 * @param entity
	 * @return
	 */
	public List<Funcionario> findBySuperior(Funcionario entity);
	
	/**
	 * @param nome
	 * @return
	 */
	public Funcionario findByNome(String nome);

	/**
	 * @param cliId
	 * @return
	 */
	List<Funcionario> findBySiteCliente(int cliId);

	/**
	 * @param sitId
	 * @return
	 */
	List<Funcionario> findBySite(int sitId);
}
