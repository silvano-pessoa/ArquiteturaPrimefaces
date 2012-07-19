/**
 * 
 */
package br.com.silvanopessoa.exception.java;

/**
 * @author silvano.pessoa
 *
 */
public class BusinessMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4242057167747464739L;

	/**
	 * 
	 */
	public BusinessMessageException() {

	}

	/**
	 * @param message
	 */
	public BusinessMessageException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BusinessMessageException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessMessageException(String message, Throwable cause) {
		super(message, cause);
	}

}
