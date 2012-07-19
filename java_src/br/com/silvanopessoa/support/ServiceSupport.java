package br.com.silvanopessoa.support;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import br.com.silvanopessoa.model.datatypes.UserSessionType;

/**
 * @author silvano.pessoa
 *
 */
public class ServiceSupport implements Serializable{

	
	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -740662919909008974L;
	
	/** Objeto responsavel por obter manipular properties */
	@Autowired
    public MessageSource messages;
	
	/**************************************************************/
	/********************* FUNCIONALIDADES ************************/
	/**************************************************************/
	
	/**
	 * Obtem a mensagem conforme o locale informado na sessão.
	 * 
	 * @param msg Mensagem.
	 * @return Retorna a mensagem.
	 */
	protected String getMessage(String msg){
        return messages.getMessage(msg,null,this.getLocale());
    }
	
	/**
	 * Obtem a mensagem conforme o locale informado na sessão e subtitui itens.
	 * 
	 * @param msg Mensagem
	 * @param identificador Objetos a serem substituidos na mensagem.
	 * @return Retorna a mensagem.
	 */
	protected String getMessage(String msg,Object[] identificador){
        return messages.getMessage(msg,identificador,this.getLocale());
    }
	
	/**
	 * Obtem a locale informado na sessão.
	 * 
	 * @return Retorna o locale.
	 */
	protected Locale getLocale(){
		Locale locale = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null){
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			locale =  (Locale) session.getAttribute(UserSessionType.LOCALE.getKey());
		}
		if (locale == null)
			locale = new Locale("pt","BR");
		return locale;
	}
}
