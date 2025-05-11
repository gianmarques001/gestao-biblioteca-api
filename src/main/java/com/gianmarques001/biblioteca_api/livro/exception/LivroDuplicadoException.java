package com.gianmarques001.biblioteca_api.livro.exception;

public class LivroDuplicadoException extends RuntimeException {
    public LivroDuplicadoException(String message) {
        super(message);
    }
}
