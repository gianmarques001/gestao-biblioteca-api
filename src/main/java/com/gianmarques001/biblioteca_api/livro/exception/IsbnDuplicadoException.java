package com.gianmarques001.biblioteca_api.livro.exception;

public class IsbnDuplicadoException extends RuntimeException {
    public IsbnDuplicadoException(String message) {
        super(message);
    }
}
