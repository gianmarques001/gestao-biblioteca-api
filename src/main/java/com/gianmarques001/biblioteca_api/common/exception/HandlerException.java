package com.gianmarques001.biblioteca_api.common.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.gianmarques001.biblioteca_api.auth.exception.EntidadeNaoEncontradaException;
import com.gianmarques001.biblioteca_api.auth.exception.JwtInvalidoException;
import com.gianmarques001.biblioteca_api.common.model.MensagemDeErro;
import com.gianmarques001.biblioteca_api.emprestimo.exception.EmprestimoDuplicadoException;
import com.gianmarques001.biblioteca_api.emprestimo.exception.EmprestimoEncerradoException;
import com.gianmarques001.biblioteca_api.emprestimo.exception.LimiteEstouradoException;
import com.gianmarques001.biblioteca_api.livro.exception.IsbnDuplicadoException;
import com.gianmarques001.biblioteca_api.livro.exception.LivroDuplicadoException;
import com.gianmarques001.biblioteca_api.livro.exception.LivroIndisponivelException;
import com.gianmarques001.biblioteca_api.taxa.exception.TaxaException;
import com.gianmarques001.biblioteca_api.usuario.exception.EmailDuplicadoException;
import com.gianmarques001.biblioteca_api.usuario.exception.SenhaIncorretaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<MensagemDeErro> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDeErro(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(LivroDuplicadoException.class)
    public ResponseEntity<MensagemDeErro> handleLivroDuplicadoException(LivroDuplicadoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemDeErro(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(IsbnDuplicadoException.class)
    public ResponseEntity<MensagemDeErro> handleIsbnDuplicadoException(IsbnDuplicadoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemDeErro(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemDeErro> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MensagemDeErro(request, HttpStatus.UNPROCESSABLE_ENTITY, "Alguns campos estão inválidos", result));
    }

    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<MensagemDeErro> handleLivroIndisponivelException(LivroIndisponivelException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemDeErro(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(LimiteEstouradoException.class)
    public ResponseEntity<MensagemDeErro> handleLimiteEstouradoException(LimiteEstouradoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensagemDeErro(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(EmprestimoDuplicadoException.class)
    public ResponseEntity<MensagemDeErro> handleEmprestimoDuplicadoException(EmprestimoDuplicadoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemDeErro(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(EmprestimoEncerradoException.class)
    public ResponseEntity<MensagemDeErro> handleEmprestimoEncerradoException(EmprestimoEncerradoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDeErro(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<MensagemDeErro> handleJWTCreationException(JWTCreationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDeErro(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


    @ExceptionHandler(JwtInvalidoException.class)
    public ResponseEntity<MensagemDeErro> handleJwtInvalidoException(JwtInvalidoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new MensagemDeErro(request, HttpStatus.BAD_GATEWAY, ex.getMessage()));
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<MensagemDeErro> handleEmailDuplicadoException(EmailDuplicadoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemDeErro(request, HttpStatus.CONFLICT, ex.getMessage()));
    }


    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<MensagemDeErro> handleSenhaIncorretaException(SenhaIncorretaException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDeErro(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


    @ExceptionHandler(TaxaException.class)
    public ResponseEntity<MensagemDeErro> handleCalculoTaxaException(TaxaException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDeErro(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MensagemDeErro> accessDeniedException(RuntimeException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MensagemDeErro(request, HttpStatus.FORBIDDEN, exception.getMessage()));
    }
}
