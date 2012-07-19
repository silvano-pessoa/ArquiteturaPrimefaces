package br.com.silvanopessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.servlet.http.HttpSession;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.silvanopessoa.model.datatypes.UserSessionType;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Menu;
import br.com.silvanopessoa.service.IMenuBarService;
import br.com.silvanopessoa.util.FacesUtils;

@Controller("menubarController")
@Scope("request")
public class MenuBarController implements Serializable {
	
	private static final long serialVersionUID = 5239625358688760829L;
	
	/**
	 * SERVICE
	 */
	@Resource
	IMenuBarService service;
	
	private MenuModel menu;
	
	/**
	 * INICIALIZACAO
	 */
	@PostConstruct
	public void init() {
		this.createMenu();
	}
	
	
	
	public void createMenu() {
		if(this.menu == null) {
			HttpSession session = FacesUtils.getSession(false);
			Accesslist accesslist = (Accesslist) session.getAttribute(UserSessionType.ACCESSLIST.getKey());

			this.menu = new DefaultMenuModel();
			if(accesslist != null) {
				List<Menu> root = this.service.getRootMenu(accesslist);
				addMenus(this.menu.getContents(), accesslist, root);
			}
			
			//addMenuItem(this.menu.getContents(), FacesUtils.i18nMessage("app.menu.label.logout"), FacesUtils.getExpressionFactory("#{loginController.logout}"));
		}
	}
	
	private void addMenus(List<UIComponent> children, Accesslist accesslist, List<Menu> menus) {
		if(menus != null && !menus.isEmpty()) {
			List<Menu> submenus;
			String label;
			for(Menu menu : menus) {
				submenus = this.service.getSubmenusToMenu(accesslist, menu);
				label = FacesUtils.i18nMessage(menu.getNomeInt());
				if(submenus == null || submenus.isEmpty()) {
					addMenuItem(children, label, FacesUtils.getExpressionFactory("#{navigationController.redirectURL('" + menu.getUrl() + "', '" + label + "')}", 
							String.class, 
							new Class<?>[] { String.class, String.class }));
				} else {
					Submenu submenu = addSubmenu(children, label);
					addMenus(submenu.getChildren(), accesslist, submenus);
				}
			}
		}
	}

	private void addMenuItem(List<UIComponent> children, String labelMenu, MethodExpression methodExpression) {
		MenuItem item = new MenuItem();
		item.setValue(labelMenu);
		item.setActionExpression(methodExpression);
		children.add(item);
	}
	
	private Submenu addSubmenu(List<UIComponent> children, String labelMenu) {
		Submenu submenu = new Submenu();
		submenu.setLabel(labelMenu);
		children.add(submenu);
		
		return submenu;
	}
	
	public MenuModel getMenu() {
		return menu;
	}
}
