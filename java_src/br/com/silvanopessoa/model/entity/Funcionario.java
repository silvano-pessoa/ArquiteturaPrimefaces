package br.com.silvanopessoa.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import br.com.silvanopessoa.annotation.IgnoreUpperCase;


/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
@Table(name="funcionario")
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fun_id")
	private int funId;

	private Boolean ativo;

	private String cpf;

	@Audited
	private String nome;

	@IgnoreUpperCase
	private String senha;

	@IgnoreUpperCase
	private String user;

	//bi-directional many-to-one association to Accesslist
    @ManyToOne
	@JoinColumn(name="acl_id")
	private Accesslist accesslist;

	//bi-directional many-to-one association to Funcionario
    @ManyToOne
	@JoinColumn(name="superior_fun_id")
	private Funcionario superior;

	@Transient
	private String novaSenha;

	@Transient
	private String validNovaSenha;
	
    public Funcionario() {
    }
 
	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getValidNovaSenha() {
		return validNovaSenha;
	}

	public void setValidNovaSenha(String validNovaSenha) {
		this.validNovaSenha = validNovaSenha;
	}

	public int getFunId() {
		return this.funId;
	}

	public void setFunId(int funId) {
		this.funId = funId;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Accesslist getAccesslist() {
		return this.accesslist;
	}

	public void setAccesslist(Accesslist accesslist) {
		this.accesslist = accesslist;
	}
	
	public Funcionario getSuperior() {
		return superior;
	}

	public void setSuperior(Funcionario superior) {
		this.superior = superior;
	}

}