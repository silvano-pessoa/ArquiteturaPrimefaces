package br.com.silvanopessoa.model.entity;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import br.com.silvanopessoa.interceptor.auditor.CustomRevisionInterceptor;

@Entity
@RevisionEntity(CustomRevisionInterceptor.class)
public class CustomRevisionEntity extends DefaultRevisionEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8133902774469260271L;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
