package br.com.silvanopessoa.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import br.com.silvanopessoa.annotation.FlexViewScope;
import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.service.impl.LoginService;
import br.com.silvanopessoa.util.FacesUtils;

@Controller("loginController")
@FlexViewScope
public class LoginController implements Serializable {
	

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -3476967062190380039L;
	
	/********* SERVICE *********/
	@Resource
	private LoginService loginService;

	/*********** VIEW **********/
	private int count;
	
	private String nome;
	
	private String senha;
	
	/**************************************************************/
	/*********************** INICIALIZACAO ************************/
	/**************************************************************/
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {

	}
	
	@PreDestroy
	public void destroy() {

	}
	
	/**************************************************************/
	/********************* REQUEST/RESPONSE ***********************/
	/**************************************************************/
	
	public String login(){
		try {
			loginService.requestAutenticarUsuario(nome, senha);
		} catch (BusinessMessageException e) {
			FacesUtils.info(e.getMessage());
			return null;
		} catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
			return null;
		}
		return "Home";
	}
	
	/**************************************************************/
	/************************** GET/SET ***************************/
	/**************************************************************/
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
    public int getCount() {  
        return count;  
    }  
    public void setCount(int count) {  
        this.count = count;  
    }  
    
	public void increment() {  
	        count++;  
	}  

}
