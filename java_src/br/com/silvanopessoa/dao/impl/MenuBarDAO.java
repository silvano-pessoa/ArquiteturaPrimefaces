package br.com.silvanopessoa.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.silvanopessoa.dao.IMenuBarDAO;
import br.com.silvanopessoa.model.entity.Menu;
import br.com.silvanopessoa.support.DAOSupport;

@Repository
public class MenuBarDAO  extends DAOSupport implements IMenuBarDAO{

	/** Serial Version UID */
	private static final long serialVersionUID = -8653278655634143555L;
	
	//TODO: PENDENTE: PADRONIZAR
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IMenuBarDAO#getRootMenu()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getRootMenu() {
		StringBuilder sql = new StringBuilder();
		sql.append("select mnu ");
		sql.append("from Menu mnu ");
		sql.append("where mnu.menu.mnuId IS NULL ");
		
		return entityManager.createQuery(sql.toString()).getResultList();
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IMenuBarDAO#getRootMenu(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getRootMenu(int aclId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select mnu ");
		sql.append("from Menu mnu ");
		sql.append("inner join mnu.accesslists acs ");
		sql.append("where mnu.menu.mnuId IS NULL ");
		sql.append("AND acs.aclId = :accesslist");
		
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("accesslist", aclId);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IMenuBarDAO#getSubmenusToMenu(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getSubmenusToMenu(int aclId, int mnuIdPai) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mnuIdPai", mnuIdPai);
		params.put("accesslist", aclId);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select mnu ");
		sql.append("from Menu mnu ");
		sql.append("inner join mnu.accesslists acs ");
		sql.append("where mnu.menu.mnuId = :mnuIdPai ");
		sql.append("AND acs.aclId = :accesslist");
		
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("mnuIdPai", mnuIdPai);
		query.setParameter("accesslist", aclId);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IMenuBarDAO#searchById(java.lang.Integer)
	 */
	@Override
	public Menu searchById(Integer id) {
		return entityManager.find(Menu.class, id);
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.dao.IMenuBarDAO#getSubmenusToMenu(br.com.silvanopessoa.model.entity.Menu)
	 */
	@Override
	@SuppressWarnings({"unchecked" })
	public List<Menu> getSubmenusToMenu(Menu menuPai) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select mnu ");
		sql.append("from Menu mnu ");
		sql.append("where mnu.menu.mnuId = :mnuIdPai ");
		
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("mnuIdPai", menuPai.getMnuId());
		return query.getResultList();
	}

}
