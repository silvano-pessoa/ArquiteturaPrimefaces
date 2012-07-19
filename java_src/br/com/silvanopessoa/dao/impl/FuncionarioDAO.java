package br.com.silvanopessoa.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.silvanopessoa.dao.IFuncionarioDAO;
import br.com.silvanopessoa.model.entity.Funcionario;
import br.com.silvanopessoa.support.DAOSupport;

@Repository
public class FuncionarioDAO extends DAOSupport implements IFuncionarioDAO {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -2668133948384808013L;	

	/**************************************************************/
	/****************** PERSISTENCIA DE DADOS *********************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#save(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	public void save(Funcionario entity){
		this.entityManager.persist(entity);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#update(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	public void update(Funcionario entity){
		this.entityManager.merge(entity);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#delete(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	public void delete(Funcionario entity){
		Funcionario funcionario	= this.findById(entity.getFunId());
		this.entityManager.remove(funcionario);
	}
	
	/**************************************************************/
	/*********************** CONSULTAS HQL ************************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#findById(int)
	 */
	@Override
	public Funcionario findById(int id){
		if (id == 0) return null;
		return entityManager.find(Funcionario.class, id);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Funcionario> findAll() {
		return entityManager.createNamedQuery("hql.funcionario.all").getResultList();
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#find(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Funcionario> find(Funcionario entity){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.find");
		query.setParameter("nome", entity.getNome() == null ? null :  "%" + entity.getNome() + "%");
		query.setParameter("ativo", entity.getAtivo() == null? null : entity.getAtivo());
		List<Funcionario> list =query.getResultList();
		return  list;
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#findByNome(java.lang.String)
	 */
	@Override
	public Funcionario findByNome(String nome){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.nome");
		query.setParameter("nome", nome);
		
		Funcionario funcionario	= (Funcionario) query.getResultList().get(0);
		return funcionario;
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#hasDuplicidade(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	public boolean hasDuplicidade(Funcionario entity){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.duplicado");
		query.setParameter("user", entity.getUser() == null ? null :  entity.getUser());
		query.setParameter("cpf", entity.getCpf() == null ? null :  entity.getCpf());
		query.setParameter("id", entity.getFunId());
		
		return query.getResultList().size()>0;
	}	

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#getByUser(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Funcionario getByUser(String user){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.user");
		query.setParameter("user", user);
		
		List<Funcionario> list	= query.getResultList();
		
		return list.get(0);
	}	

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#findBySuperior(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Funcionario> findBySuperior(Funcionario entity){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.findSuperior");
		query.setParameter("id", entity.getFunId());
		
		List<Funcionario> list =query.getResultList();
		return  list;		
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#findBySiteCliente(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Funcionario> findBySiteCliente(int cliId){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.site.cliente");
		query.setParameter("cliId", cliId);
		List<Funcionario> list =query.getResultList();
		return  list;		
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IFuncionarioDAO#findBySite(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Funcionario> findBySite(int sitId){
		Query query =(Query) entityManager.createNamedQuery("hql.funcionario.site");
		query.setParameter("sitId", sitId);
		List<Funcionario> list =query.getResultList();
		return  list;		
	}
	
}