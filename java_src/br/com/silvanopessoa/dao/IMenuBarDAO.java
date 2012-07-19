package br.com.silvanopessoa.dao;

import java.util.List;

import br.com.silvanopessoa.model.entity.Menu;

public interface IMenuBarDAO {

	public List<Menu> getRootMenu();
	public List<Menu> getRootMenu(int aclId);
	public List<Menu> getSubmenusToMenu(int aclId, int mnuIdPai);
	public Menu searchById(Integer id);
	public List<Menu> getSubmenusToMenu(Menu menuPai);
}
