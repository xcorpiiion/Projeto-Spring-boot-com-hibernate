package br.com.estudo.projetoweb.services.exception;

public class DataIntegratydException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegratydException(String message) {
        super(message);
    }

    public DataIntegratydException(String message, Throwable cause) {
        super(message, cause);
    }
}
