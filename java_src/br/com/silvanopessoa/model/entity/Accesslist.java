package br.com.silvanopessoa.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the accesslist database table.
 * 
 */
@Entity
@Table(name="accesslist")
public class Accesslist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="acl_id")
	private int aclId;

	private String descr;

	private String nome;

	//bi-directional many-to-many association to Checkpoint
    @ManyToMany
	@JoinTable(
		name="accesslist_checkpoint"
		, joinColumns={
			@JoinColumn(name="acl_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="chk_id")
			}
		)
	private List<Checkpoint> checkpoints;

	//bi-directional many-to-many association to Menu
    @ManyToMany
	@JoinTable(
		name="accesslist_menu"
		, joinColumns={
			@JoinColumn(name="acl_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="mnu_id")
			}
		)
	private List<Menu> menus;

	//bi-directional many-to-one association to Funcionario
	@OneToMany(mappedBy="accesslist")
	private List<Funcionario> funcionarios;

	@Transient
	private List<Checkpoint> checkpointsDelete;
	
    public List<Checkpoint> getCheckpointsDelete() {
		return checkpointsDelete;
	}

	public void setCheckpointsDelete(List<Checkpoint> checkpointsDelete) {
		this.checkpointsDelete = checkpointsDelete;
	}

	public Accesslist() {
    }

	public int getAclId() {
		return this.aclId;
	}

	public void setAclId(int aclId) {
		this.aclId = aclId;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Checkpoint> getCheckpoints() {
		return this.checkpoints;
	}

	public void setCheckpoints(List<Checkpoint> checkpoints) {
		this.checkpoints = checkpoints;
	}
	
	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	public List<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
}