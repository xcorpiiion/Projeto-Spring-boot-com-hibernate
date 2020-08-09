package br.com.estudo.projetoweb.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private List<FieldMessage> fieldMessages = new ArrayList<>();

    public ValidationError(Integer status, String mensagem, Long timeStamp) {
        super(status, mensagem, timeStamp);
    }

    public List<FieldMessage> getFieldMessages() {
        return fieldMessages;
    }

    public void addError(String fieldName, String message) {
        fieldMessages.add(new FieldMessage(fieldName, message));
    }
}
