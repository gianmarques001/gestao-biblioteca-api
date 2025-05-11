package com.gianmarques001.biblioteca_api.auth.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtInvalidoException extends JWTVerificationException {
    public JwtInvalidoException(String message) {
        super(message);
    }
}
