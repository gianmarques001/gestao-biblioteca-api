package com.gianmarques001.biblioteca_api.emprestimo.exception;

public class EmprestimoDuplicadoException extends RuntimeException {
    public EmprestimoDuplicadoException(String message) {
        super(message);
    }
}
