package br.com.silvanopessoa.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the checkpoint database table.
 * 
 */
@Entity
@Table(name="checkpoint")
public class Checkpoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="chk_id")
	private int chkId;

	private String descr;

	private String nome;

	//bi-directional many-to-many association to Accesslist
	@ManyToMany(mappedBy="checkpoints")
	private List<Accesslist> accesslists;

    public Checkpoint() {
    }

	public int getChkId() {
		return this.chkId;
	}

	public void setChkId(int chkId) {
		this.chkId = chkId;
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

	public List<Accesslist> getAccesslists() {
		return this.accesslists;
	}

	public void setAccesslists(List<Accesslist> accesslists) {
		this.accesslists = accesslists;
	}
	
}