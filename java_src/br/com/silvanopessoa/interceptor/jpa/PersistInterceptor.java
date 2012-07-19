package br.com.silvanopessoa.interceptor.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.MethodInvocationException;

import br.com.silvanopessoa.annotation.IgnoreUpperCase;
import br.com.silvanopessoa.exception.java.ApplicationMessageException;

public class PersistInterceptor {

	private static final String GET_INITIALS = "get";
	
	private static final String SET_INITIALS = "set";
	
	@PrePersist
	@PreUpdate
	@SuppressWarnings("rawtypes") 
	public void onSaveOrUpdate(Object object) throws ApplicationMessageException{
    	Class objectClass = object.getClass();

	    for (Field field : objectClass.getDeclaredFields()) {
	        if (field.isAnnotationPresent(IgnoreUpperCase.class))
	            continue;
	        
	        if (field.getType().equals(String.class)) {
	        	try {
	                Method getter = findGetter(objectClass, field.getName());
	                Method setter = findSetter(objectClass, field.getName());
	                String value = (String) getter.invoke(object);
	                if (value != null && setter !=null) {
	                    setter.invoke(object, value.toUpperCase());
	                }
		        } catch (MethodInvocationException e) {
	                e.printStackTrace();
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (SecurityException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            } catch (InvocationTargetException e) {
	                e.printStackTrace();
	            }
	            catch (NullPointerException e) {
                e.printStackTrace();
               }
	        }
	    	
	    }
	    
	}
    
	/**
	 * @param type
	 * @param property
	 * @return
	 * @throws ApplicationMessageException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method findGetter(Class type,  String property) throws ApplicationMessageException {
		String methodName = GET_INITIALS + Character.toUpperCase(property.charAt(0)) + property.substring(1);
		try {
			return type.getMethod(methodName);
		} catch (SecurityException e) {
			throw new ApplicationMessageException("Não encontrado o método getter " + property, e);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * @param current
	 * @param property
	 * @return
	 * @throws ApplicationMessageException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method findSetter(Class current, String property) throws ApplicationMessageException {
		String methodName = SET_INITIALS + upperCaseFirstLetter(property);
		try {
			return current.getMethod(methodName,String.class);
		} catch (SecurityException e) {
			throw new ApplicationMessageException("Não encontrado o método setter" + property, e);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param property
	 * @return
	 */
	private static String upperCaseFirstLetter(String property) {
		return Character.toUpperCase(property.charAt(0)) + property.substring(1);
	}
	
}
