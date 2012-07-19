package br.com.silvanopessoa.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.silvanopessoa.model.entity.Language;
import br.com.silvanopessoa.service.ISessionService;

@Controller("languageController")
@Scope("session")
public class LanguageController implements Serializable {

	private static List<Language> countries;
	
	static {
		countries = new ArrayList<Language>();
		countries.add(new Language("Português (Brasil)", new Locale("pt", "BR")));
		countries.add(new Language("English", new Locale("en", "US")));
	}
	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -3028578611213048049L;

	private String localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	
	
	@Resource
	ISessionService sessionService;

	
	/**************************************************************/
	/*********************** INICIALIZACAO ************************/
	/**************************************************************/
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		Locale locale = new Locale("pt", "BR");
		//Locale locale = new Locale("en", "US");
		//RN01 - SETA O LOCALE NO CONTEXTO
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		//RN02 - SETA O LOCALE NA SESSION
		sessionService.setLocaleSession(locale);
	}
	
	/**************************************************************/
	/********************* REQUEST/RESPONSE ***********************/
	/**************************************************************/
	
	
	/**************************************************************/
	/************************** GET/SET ***************************/
	/**************************************************************/
	
	public String getLocaleCode() {
		localeCode =sessionService.getLocaleSession().toString();
		return localeCode;
	}
	
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public List<Language> getCountries() {
		return countries;
	}

	public void changeLocaleCode(ValueChangeEvent e) {
		String newLocaleValue = e.getNewValue().toString();
		
		for(Language language : countries) {
			if(language.getLocale().toString().equals(newLocaleValue)) {
				localeCode = language.getLocale().toString();
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)language.getLocale());
			}
		}
	}
	
}