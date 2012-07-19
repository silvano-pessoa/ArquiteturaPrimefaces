package br.com.silvanopessoa.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.silvanopessoa.model.entity.Funcionario;

@FacesConverter("funcionarioConverter")
public class FuncionarioConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if( value != null ) {
			return this.getAttributesFrom(component).get( value );
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if( value != null && !value.equals("") ) {
			Funcionario funcionario = (Funcionario) value;
			
			this.addAttribute(component, funcionario);
			
			Integer codigo = funcionario.getFunId();
			if ( codigo != null ){
				return String.valueOf( codigo );
			}
		}
		
		return (String) value;
	}
	
	protected void addAttribute(UIComponent component, Funcionario o) {
		String key = Integer.valueOf(o.getFunId()).toString(); // codigo do Funcionario como chave neste caso
		this.getAttributesFrom(component).put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
}
