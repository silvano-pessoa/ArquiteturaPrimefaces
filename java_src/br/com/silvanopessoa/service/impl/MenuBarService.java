package br.com.silvanopessoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.silvanopessoa.dao.IMenuBarDAO;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Menu;
import br.com.silvanopessoa.service.IMenuBarService;
import br.com.silvanopessoa.support.ServiceSupport;

@Service
public class MenuBarService extends ServiceSupport implements IMenuBarService{

	/** Serial Version UID */
	private static final long serialVersionUID = 3670969512326036455L;
	
	/**	DAO */
	@Resource
	IMenuBarDAO dao;
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#getRootMenu()
	 */
	@Override
	public List<Menu> getRootMenu() {
		return this.dao.getRootMenu();
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#getRootMenu(br.com.silvanopessoa.model.entity.Accesslist)
	 */
	@Override
	public List<Menu> getRootMenu(Accesslist accesslist) {
		return this.dao.getRootMenu(accesslist.getAclId());
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#getSubmenusToMenu(br.com.silvanopessoa.model.entity.Menu)
	 */
	@Override
	public List<Menu> getSubmenusToMenu(Menu menuPai) {
		return this.dao.getSubmenusToMenu(menuPai);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#getSubmenusToMenu(br.com.silvanopessoa.model.entity.Accesslist, br.com.silvanopessoa.model.entity.Menu)
	 */
	@Override
	public List<Menu> getSubmenusToMenu(Accesslist accesslist, Menu menuPai) {
		return this.dao.getSubmenusToMenu(accesslist.getAclId(), menuPai.getMnuId());
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#searchById(java.lang.Integer)
	 */
	@Override
	public Menu searchById(Integer id){
		return this.dao.searchById(id); 
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#getRootMenu(int)
	 */
	@Override
	public List<Menu> getRootMenu(int aclId) {
		return dao.getRootMenu(aclId);
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.IMenuBarService#getSubmenusToMenu(int, int)
	 */
	@Override
	public List<Menu> getSubmenusToMenu(int aclId, int mnuIdPai) {
		return dao.getSubmenusToMenu(aclId, mnuIdPai);
	}
}
