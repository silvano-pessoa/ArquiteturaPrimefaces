package br.com.silvanopessoa.service.impl;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.stereotype.Service;

import br.com.silvanopessoa.service.IGuestPreferences;
import br.com.silvanopessoa.support.ServiceSupport;

@Service
public class GuestPreferences extends ServiceSupport implements IGuestPreferences {
	private static final long serialVersionUID = 255121243861811064L;
	
	private String theme = "flick"; //default

	public String getTheme() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(params.containsKey("theme")) {
			theme = params.get("theme");
		}
		
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
}
