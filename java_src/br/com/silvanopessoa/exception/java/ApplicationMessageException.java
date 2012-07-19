/**
 * 
 */
package br.com.silvanopessoa.exception.java;

/**
 * @author silvano.pessoa
 *
 */
public class ApplicationMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1954424776244824128L;

	/**
	 * 
	 */
	public ApplicationMessageException() {
	}

	/**
	 * @param message
	 */
	public ApplicationMessageException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ApplicationMessageException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationMessageException(String message, Throwable cause) {
		super(message, cause);
	}

}
