package br.com.silvanopessoa.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.silvanopessoa.model.entity.Accesslist;



@FacesConverter("accesslistConverter")
public class AccesslistConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if( value != null ) {
			return this.getAttributesFrom(component).get( value );
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if( value != null && !value.equals("") ) {
			Accesslist accessList = (Accesslist) value;
			
			this.addAttribute(component, accessList);
			
			Integer codigo = accessList.getAclId();
			if ( codigo != null ){
				return String.valueOf( codigo );
			}
		}
		
		return (String) value;
	}
	
	protected void addAttribute(UIComponent component, Accesslist o) {
		String key = Integer.valueOf(o.getAclId()).toString(); // codigo do accesslist como chave neste caso
		this.getAttributesFrom(component).put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
}
