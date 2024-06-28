package br.com.example.ForumHub.infra.exception;

public class APIValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APIValidationException(String message) {
		super(message);
	}
}
