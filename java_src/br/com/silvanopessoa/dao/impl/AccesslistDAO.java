package br.com.silvanopessoa.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.silvanopessoa.dao.IAccesslistDAO;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.support.DAOSupport;

@Repository
public class AccesslistDAO extends DAOSupport implements IAccesslistDAO {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -2668133948384808013L;	
	
	/**************************************************************/
	/****************** PERSISTENCIA DE DADOS *********************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#save(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	public void save(Accesslist entity){
		this.entityManager.persist(entity);
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#update(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	public void update(Accesslist entity){
		this.entityManager.merge(entity);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#delete(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	public void delete(Accesslist entity){
		Accesslist accesslist	= this.findById(entity.getAclId());
		this.entityManager.remove(accesslist);
	}
	
	/**************************************************************/
	/*********************** CONSULTAS HQL ************************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#findById(int)
	 */
	@Override
	public Accesslist findById(int id){
		if (id == 0) return null;
		return entityManager.find(Accesslist.class, id);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Accesslist> findAll() {
		return entityManager.createNamedQuery("hql.accesslist.all").getResultList();
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#find(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Accesslist> find(Accesslist entity){
		Query query =(Query) entityManager.createNamedQuery("hql.accesslist.find");
		query.setParameter("nome", entity.getNome() == null ? null :  "%" + entity.getNome() + "%");
		return query.getResultList();
	}
		
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IAccesslistDAO#hasDuplicidade(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	public boolean hasDuplicidade(Accesslist entity){
		Query query =(Query) entityManager.createNamedQuery("hql.accesslist.findNome");
		query.setParameter("nome", entity.getNome() == null ? null :  entity.getNome());
		query.setParameter("id", entity.getAclId());
		return query.getResultList().size()>0;
	}
}