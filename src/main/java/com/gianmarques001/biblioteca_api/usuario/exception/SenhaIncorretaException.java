package com.gianmarques001.biblioteca_api.usuario.exception;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }
}
