package br.com.silvanopessoa.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;

public class FacesUtils {
	
	//private static Logger logger = Logger.getLogger(FacesUtils.class);
	
	public static String i18nMessage(String keyMessage) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "i18n");
		return bundle.getString(keyMessage); 	
	}
	
	public static void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO, message, null ));  
	}
	
	public static void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_WARN, message, null ));
	}
	
	public static void warnI18n(String keyMessage) {
		FacesUtils.warn(FacesUtils.i18nMessage(keyMessage));
	}
	
	public static void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR, message, null ));
	}
	
	public static void errorI18n(String keyMessage) {
		FacesUtils.error(FacesUtils.i18nMessage(keyMessage));
	}
	
	public static void infoI18n(String keyMessage) {
		FacesUtils.info(FacesUtils.i18nMessage(keyMessage));
	}
	
	public static void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_FATAL, message, null ));
	}
	
	public static void redirect(String url) throws ApplicationMessageException {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); 
			String requestContext = externalContext.getRequestContextPath();
			externalContext.redirect(requestContext + url);
		} catch (IOException e) {
			throw new ApplicationMessageException("app.generic.error", e);
		}
	}
	
	public static HttpSession getSession(boolean create) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(create);
		return session;
	}

	public static HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request;
	}

	public static MethodExpression getExpressionFactory(String expression) {
		return FacesUtils.getExpressionFactory(expression, null, new Class[]{});
	}
	
	public static MethodExpression getExpressionFactory(String expression, Class<?> expectedReturnType, Class<?>[] expectedParamType) {
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext elContext = context.getELContext();
		Application app = context.getApplication();
		ExpressionFactory expFactory = app.getExpressionFactory();
		return expFactory.createMethodExpression(elContext, expression, expectedReturnType, expectedParamType);
	}
	
	public static String getLocale(){
		return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
	}

	/**
	 * Limpa todas as validações do formulario
	 */
	public static void clearForm() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		PartialViewContext ajaxContext = facesContext.getPartialViewContext();
		UIComponent root = facesContext.getViewRoot();
		Collection<String> renderIds = ajaxContext.getRenderIds();
		for (String renderId : renderIds) {
			UIComponent form = findComponent(root, renderId);
			if (form != null) {
				clearComponentHierarchy(form);
			}
		}
	}

	private static void clearComponentHierarchy(UIComponent pComponent) {

		if (pComponent.isRendered()) {

			if (pComponent instanceof EditableValueHolder) {
				EditableValueHolder editableValueHolder = (EditableValueHolder) pComponent;
				editableValueHolder.setSubmittedValue(null);
				editableValueHolder.setValue(null);
				editableValueHolder.setLocalValueSet(false);
				editableValueHolder.setValid(true);
			}

			for (Iterator<UIComponent> iterator = pComponent
					.getFacetsAndChildren(); iterator.hasNext();) {
				clearComponentHierarchy(iterator.next());
			}

		}
	}

	@SuppressWarnings("rawtypes")
	private static UIComponent findComponent(UIComponent base, String id) {
		if (id.equals(base.getId()))
			return base;
		UIComponent kid = null;
		UIComponent result = null;
		Iterator kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				break;
			}
			result = findComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}
}