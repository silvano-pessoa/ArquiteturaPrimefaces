package br.com.silvanopessoa.controller;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.silvanopessoa.service.ISessionService;

@Scope("session")
@Controller("sessionController")
public class SessionController implements Serializable{

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/

	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -2045489745210006017L;


	/********* SERVICE *********/
	@Resource
	private ISessionService service;
	
	/*********** VIEW **********/
	private String userName;

	/**************************************************************/
	/*********************** INICIALIZACAO ************************/
	/**************************************************************/
	
	
	/**************************************************************/
	/********************* REQUEST/RESPONSE ***********************/
	/**************************************************************/
	

	
	/**************************************************************/
	/************************** GET/SET ***************************/
	/**************************************************************/

	public String getUserName() {
		userName = service.getUserSession().toString();
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
