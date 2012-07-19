package br.com.silvanopessoa.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.silvanopessoa.dao.ICheckpointDAO;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Checkpoint;
import br.com.silvanopessoa.support.DAOSupport;

@Repository
public class CheckpointDAO extends DAOSupport implements ICheckpointDAO {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -2668133948384808013L;	
	
	/**************************************************************/
	/****************** PERSISTENCIA DE DADOS *********************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#save(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	public void save(Checkpoint entity){
		this.entityManager.persist(entity);
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#update(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	public void update(Checkpoint entity){
		this.entityManager.merge(entity);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#delete(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	public void delete(Checkpoint entity){
		Checkpoint checkpoint	= this.findById(entity.getChkId());
		this.entityManager.remove(checkpoint);
	}
	
	/**************************************************************/
	/*********************** CONSULTAS HQL ************************/
	/**************************************************************/
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#findById(int)
	 */
	@Override
	public Checkpoint findById(int id){
		if (id == 0) return null;
		return entityManager.find(Checkpoint.class, id);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Checkpoint> findAll() {
		return entityManager.createNamedQuery("hql.checkpoint.all").getResultList();
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#find(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Checkpoint> find(Checkpoint entity){
		Query query =(Query) entityManager.createNamedQuery("hql.checkpoint.find");
		query.setParameter("nome", entity.getNome() == null ? null :  "%" + entity.getNome() + "%");

		List<Checkpoint> list =query.getResultList();
		return list;
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#hasDuplicidade(br.com.silvanopessoa.model.entity.Checkpoint)
	 */
	@Override
	public boolean hasDuplicidade(Checkpoint entity){
		Query query =(Query) entityManager.createNamedQuery("hql.checkpoint.findNome");
		query.setParameter("nome", entity.getNome() == null ? null :  entity.getNome());
		query.setParameter("id", entity.getChkId());
		return query.getResultList().size()>0;
	}	
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.ICheckpointDAO#findbyAccesslist(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Checkpoint> findbyAccesslist(Accesslist entity){
		Query query =(Query) entityManager.createNamedQuery("hql.checkpoint.findAccess");
		query.setParameter("accesslist", entity.getAclId() == 0 ? null :  entity.getAclId());
		return query.getResultList();
	}		
}