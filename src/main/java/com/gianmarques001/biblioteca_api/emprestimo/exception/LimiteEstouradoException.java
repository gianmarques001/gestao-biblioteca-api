package com.gianmarques001.biblioteca_api.emprestimo.exception;

public class LimiteEstouradoException extends RuntimeException {
    public LimiteEstouradoException(String message) {
        super(message);
    }
}
