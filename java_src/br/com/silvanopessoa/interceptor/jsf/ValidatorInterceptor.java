/**
 * 
 */
package br.com.silvanopessoa.interceptor.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;

import br.com.silvanopessoa.util.FacesUtils;

/**
 * @author silvano.pessoa
 *
 */
public class ValidatorInterceptor implements MessageInterpolator, Serializable {

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/
	
	/** SERIAL VERSION UID */
	private static final long serialVersionUID = 3306898074818613615L;
	
	private MessageInterpolator interpolator;
	
	private static final Pattern PATTERN = Pattern.compile("\\{.*?\\}");
	
	/**
	 *  Construtor da Classe.
	 */
	public ValidatorInterceptor() {
		this.interpolator=Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
	}

	/* (non-Javadoc)
	 * @see javax.validation.MessageInterpolator#interpolate(java.lang.String, javax.validation.MessageInterpolator.Context)
	 */
	@Override
	public String interpolate(String messageTemplate, Context context) {
		String message = FacesUtils.i18nMessage(interpolator.interpolate(messageTemplate, context));
		return replaceParameters(message, context);
	}

	/* (non-Javadoc)
	 * @see javax.validation.MessageInterpolator#interpolate(java.lang.String, javax.validation.MessageInterpolator.Context, java.util.Locale)
	 */
	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		//context.getConstraintDescriptor().getAttributes()
		String message = FacesUtils.i18nMessage(interpolator.interpolate(messageTemplate, context, locale));
		return replaceParameters(message, context);
	}
	
	/**
	 * @param message
	 * @param context
	 * @return
	 */
	protected static String replaceParameters(String message, MessageInterpolator.Context context) {
        Matcher matcher = PATTERN.matcher(message);
        List<String> messageAttributes = new ArrayList<String>();
        while (matcher.find()) {
            messageAttributes.add(matcher.group());
        }
        if (context != null && context.getConstraintDescriptor() != null && context.getConstraintDescriptor().getAttributes() != null) {
            for (String attribute : messageAttributes) {
                Object value = context.getConstraintDescriptor().getAttributes().get(attribute.replace("{", "").replace("}", ""));
                if (value != null) {
                    message = message.replace(attribute, value.toString());
                }
            }
        }
        return message;
    }

	
//	# $Id: ValidationMessages.properties 19251 2010-04-20 15:28:18Z hardy.ferentschik $  
//	javax.validation.constraints.AssertFalse.message=must be false  
//	javax.validation.constraints.AssertTrue.message=must be true  
//	javax.validation.constraints.DecimalMax.message=must be less than or equal to {value}  
//	javax.validation.constraints.DecimalMin.message=must be greater than or equal to {value}  
//	javax.validation.constraints.Digits.message=numeric value out of bounds (<{integer} digits>.<{fraction} digits> expected)  
//	javax.validation.constraints.Future.message=must be in the future  
//	javax.validation.constraints.Max.message=must be less than or equal to {value}  
//	javax.validation.constraints.Min.message=must be greater than or equal to {value}  
//	javax.validation.constraints.NotNull.message=may not be null  
//	javax.validation.constraints.Null.message=must be null  
//	javax.validation.constraints.Past.message=must be in the past  
//	javax.validation.constraints.Pattern.message=must match "{regexp}"  
//	javax.validation.constraints.Size.message=size must be between {min} and {max}  
//	org.hibernate.validator.constraints.Email.message=not a well-formed email address  
//	org.hibernate.validator.constraints.Length.message=length must be between {min} and {max}  
//	org.hibernate.validator.constraints.NotBlank.message=may not be empty  
//	org.hibernate.validator.constraints.NotEmpty.message=may not be empty  
//	org.hibernate.validator.constraints.Range.message=must be between {min} and {max}  
//	org.hibernate.validator.constraints.URL.message=must be a valid URL  
//	org.hibernate.validator.constraints.CreditCardNumber.message=invalid credit card number  
//	org.hibernate.validator.constraints.ScriptAssert.message=script expression "{script}" didn't evaluate to true 
	
}
