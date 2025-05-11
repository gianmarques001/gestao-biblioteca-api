package com.gianmarques001.biblioteca_api.usuario.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EmailDuplicadoException extends DataIntegrityViolationException {
    public EmailDuplicadoException(String message) {
        super(message);
    }
}
