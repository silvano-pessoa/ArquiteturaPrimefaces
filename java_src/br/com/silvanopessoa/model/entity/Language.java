package br.com.silvanopessoa.model.entity;

import java.io.Serializable;
import java.util.Locale;

public class Language implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1475729583414959567L;

	private String description;
	
	private Locale locale;
	
	public Language() {}

	public Language(String description, Locale locale) {
		this.description = description;
		this.locale = locale;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return this.locale.toString();
	}
}
