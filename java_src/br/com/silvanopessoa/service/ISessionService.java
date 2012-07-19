package br.com.silvanopessoa.service;

import java.util.Locale;

import org.springframework.security.core.userdetails.User;

import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Funcionario;

public interface ISessionService {

	void initSession(Funcionario funcionario);

	User getUsuarioApplication();

	void invalidateSession();

	void logout();

	Accesslist getAccesslistSession();

	Integer getFuncionarioIdSession();

	String getUserSession();

	void setLocaleSession(Locale locale);

	Locale getLocaleSession();

}
