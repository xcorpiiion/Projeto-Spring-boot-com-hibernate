package br.com.estudo.projetoweb.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String fieldName;

    private String mensagem;

    public FieldMessage() {
    }

    public FieldMessage(String fieldName, String mensagem) {
        this.fieldName = fieldName;
        this.mensagem = mensagem;
    }
}
