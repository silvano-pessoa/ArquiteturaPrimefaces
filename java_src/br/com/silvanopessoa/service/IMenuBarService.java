package br.com.silvanopessoa.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Menu;

public interface IMenuBarService {
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Menu> getRootMenu();
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Menu> getRootMenu(int aclId);
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Menu> getSubmenusToMenu(int aclId, int mnuIdPai);
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Menu> getRootMenu(Accesslist accesslist);
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Menu> getSubmenusToMenu(Accesslist accesslist, Menu menuPai);
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public List<Menu> getSubmenusToMenu(Menu menuPai);
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Menu searchById(Integer id);
}
