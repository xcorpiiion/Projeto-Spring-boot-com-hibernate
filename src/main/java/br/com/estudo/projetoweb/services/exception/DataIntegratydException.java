package br.com.estudo.projetoweb.services.exception;

public class DataIntegratydException extends RuntimeException {

    public DataIntegratydException(String message) {
        super(message);
    }

    public DataIntegratydException(String message, Throwable cause) {
        super(message, cause);
    }
}
