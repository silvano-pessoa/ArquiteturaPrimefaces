package br.com.silvanopessoa.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;

@Controller("navigationController")
@Scope("session")
public class NavigationController implements Serializable {

	private static final long serialVersionUID = 8467929010476347307L;

	public String redirectURL(String url, String titlePage) throws ApplicationMessageException {
		return url + "?faces-redirect=true";
	}
	
	public String getTitlePage() {
//		HttpSession session = FacesUtils.getSession(false);
//		if(session != null) {
//			if(session.getAttribute(UserSessionType.ACCESSLIST.getKey()) != null) {
//				//PrettyContext context = PrettyContext.getCurrentInstance();
//				//String searchURL = context.getCurrentViewId();
//				//Map<String, String> titles = (Map<String, String>) session.getAttribute(UserSessionType.TITLE_PAGES.getKey());
//				//if(titles != null && !titles.isEmpty() && titles.containsKey(searchURL))
//				//	return titles.get(searchURL);
//			}
//		}
//		
//		return "-";
		return null;
	}
}
