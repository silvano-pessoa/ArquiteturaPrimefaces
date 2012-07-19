package br.com.silvanopessoa.model.dto;

import java.io.Serializable;
import java.util.List;

public class UsuarioSistemaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7221875536591729397L;

	private List<String> permissoes;
	
	private String usuario;

	public List<String> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<String> permissoes) {
		this.permissoes = permissoes;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
