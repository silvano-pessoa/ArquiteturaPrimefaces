package br.com.silvanopessoa.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(name="menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mnu_id")
	private int mnuId;

	@Column(name="nome_int")
	private String nomeInt;

	private String url;

	//bi-directional many-to-many association to Accesslist
	@ManyToMany(mappedBy="menus")
	private List<Accesslist> accesslists;

	//bi-directional many-to-one association to Menu
    @ManyToOne
	@JoinColumn(name="pai_mnu_id")
	private Menu menu;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="menu")
	private List<Menu> menus;

    public Menu() {
    }

	public int getMnuId() {
		return this.mnuId;
	}

	public void setMnuId(int mnuId) {
		this.mnuId = mnuId;
	}

	public String getNomeInt() {
		return this.nomeInt;
	}

	public void setNomeInt(String nomeInt) {
		this.nomeInt = nomeInt;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Accesslist> getAccesslists() {
		return this.accesslists;
	}

	public void setAccesslists(List<Accesslist> accesslists) {
		this.accesslists = accesslists;
	}
	
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
}