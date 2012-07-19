package br.com.silvanopessoa.model.dto;

import java.io.Serializable;

import br.com.silvanopessoa.model.entity.Funcionario;

public class FuncionarioDTO implements Serializable{

	private static final long serialVersionUID = 977183827791237335L;
	
	private Funcionario funcionario;
	private String nome;
	private Boolean ativo;
	
	public FuncionarioDTO() {
		super();
		
		this.funcionario	= new Funcionario();
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}	
}
