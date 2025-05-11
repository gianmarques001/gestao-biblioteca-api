package com.gianmarques001.biblioteca_api.livro.exception;

public class LivroIndisponivelException extends RuntimeException {
    public LivroIndisponivelException(String message) {
        super(message);
    }
}
