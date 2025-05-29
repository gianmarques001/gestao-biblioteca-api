package com.gianmarques001.biblioteca_api.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gianmarques001.biblioteca_api.auth.model.UserAuthDetails;
import com.gianmarques001.biblioteca_api.auth.exception.JwtInvalidoException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JwtUtils {

    private static final String SECRET = "secret";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final Long EXPIRE_MINUTES = 10L;

    public static String generateToken(UserAuthDetails userAuthDetails) {
        return JWT.create()
                .withIssuer("biblioteca-api")
                .withSubject(userAuthDetails.getEmail())
                .withExpiresAt(toExpire())
                .sign(ALGORITHM);
    }

    private static Instant toExpire() {
        return LocalDateTime.now().plusMinutes(EXPIRE_MINUTES).toInstant(ZoneOffset.of("-03:00"));
    }

    public static String verifyToken(String token) {
        try {
            return JWT.require(ALGORITHM)
                    .withIssuer("biblioteca-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JwtInvalidoException("Token inválido");
        }
    }
}
