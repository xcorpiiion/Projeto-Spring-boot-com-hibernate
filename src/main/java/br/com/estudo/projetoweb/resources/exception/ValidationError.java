package br.com.estudo.projetoweb.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> fieldMessages = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Integer status, String mensagem, Long timeStamp, String error, String path) {
		super(status, mensagem, timeStamp, error, path);
	}

	public List<FieldMessage> getFieldMessages() {
        return fieldMessages;
    }

    public void addError(String fieldName, String message) {
        fieldMessages.add(new FieldMessage(fieldName, message));
    }
}
