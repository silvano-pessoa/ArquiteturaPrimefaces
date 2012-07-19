package br.com.silvanopessoa.service;
import java.util.List;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.entity.Funcionario;

public interface IFuncionarioService {
	
	/**
	 * @param entity
	 * @throws BusinessMessageException
	 * @throws ApplicationMessageException
	 */
	public void save(Funcionario entity) throws BusinessMessageException, ApplicationMessageException;

	/**
	 * @param entity
	 * @throws ApplicationMessageException
	 * @throws BusinessMessageException
	 */
	public void delete(Funcionario entity) throws ApplicationMessageException, BusinessMessageException;

	/**
	 * @param id
	 * @return
	 * @throws ApplicationMessageException
	 */
	public Funcionario findById(int id) throws ApplicationMessageException;

	/**
	 * @param entity
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Funcionario> find(Funcionario entity) throws ApplicationMessageException;

	/**
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Funcionario> findAll() throws ApplicationMessageException;

	/**
	 * @param razao
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Funcionario> findListByNome(String nome) throws ApplicationMessageException;	

	/**
	 * @param user
	 * @return
	 * @throws ApplicationMessageException
	 */
	public Funcionario getByUser(String user) throws ApplicationMessageException;
	
	/**
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Funcionario> findByAtivos() throws ApplicationMessageException;
	
	/**
	 * @param entity
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Funcionario> findBySuperior(Funcionario entity) throws ApplicationMessageException;
	

	/**
	 * @param nome
	 * @return
	 * @throws ApplicationMessageException
	 */
	public Funcionario findByNome(String nome) throws ApplicationMessageException;

	/**
	 * @param cliId
	 * @return
	 * @throws ApplicationMessageException
	 */
	public List<Funcionario> findBySiteCliente(int cliId) throws ApplicationMessageException;

	/**
	 * @param sitId
	 * @return
	 * @throws ApplicationMessageException
	 */
	List<Funcionario> findBySite(int sitId) throws ApplicationMessageException;
}
