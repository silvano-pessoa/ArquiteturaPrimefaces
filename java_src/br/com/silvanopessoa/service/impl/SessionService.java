package br.com.silvanopessoa.service.impl;


import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.silvanopessoa.model.datatypes.UserSessionType;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Funcionario;
import br.com.silvanopessoa.service.ISessionService;
import br.com.silvanopessoa.support.ServiceSupport;
import br.com.silvanopessoa.util.FacesUtils;

@Service
public class SessionService extends ServiceSupport implements ISessionService{
	
	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = -161938433826615841L;
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#getUsuarioApplication()
	 */
	@Override
	public User getUsuarioApplication(){
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#initSession(br.com.silvanopessoa.model.entity.Funcionario)
	 */
	@Override
	public void initSession(Funcionario funcionario){
		HttpSession session = FacesUtils.getSession(true);
		session.setAttribute(UserSessionType.FUNC_ID.getKey(), funcionario.getFunId());
		session.setAttribute(UserSessionType.USER_NAME.getKey(), funcionario.getUser());
		session.setAttribute(UserSessionType.ACCESSLIST.getKey(), funcionario.getAccesslist());
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#setLocaleSession(java.util.Locale)
	 */
	@Override
	public void setLocaleSession(Locale locale){
		HttpSession session = FacesUtils.getSession(true);
		session.setAttribute(UserSessionType.LOCALE.getKey(), locale);
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#getLocaleSession()
	 */
	@Override
	public Locale getLocaleSession(){
		HttpSession session = FacesUtils.getSession(true);
		return (Locale) session.getAttribute(UserSessionType.LOCALE.getKey());
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#invalidateSession()
	 */
	@Override
	public void invalidateSession(){
		FacesContext context =  FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#logout()
	 */
	@Override
	public void logout(){
		SecurityContextHolder.getContext().setAuthentication(null);
		this.invalidateSession();
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#getAccesslistSession()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Accesslist getAccesslistSession(){
		HttpSession session = FacesUtils.getSession(true);
		return (Accesslist) session.getAttribute(UserSessionType.ACCESSLIST.getKey());
	}

	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#getFuncionarioIdSession()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Integer getFuncionarioIdSession(){
		HttpSession session = FacesUtils.getSession(true);
		return (Integer) session.getAttribute(UserSessionType.FUNC_ID.getKey());
	}
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ISessionService#getUserSession()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public String getUserSession(){
		HttpSession session = FacesUtils.getSession(true);
		return (String) session.getAttribute(UserSessionType.USER_NAME.getKey());
	}	
}
