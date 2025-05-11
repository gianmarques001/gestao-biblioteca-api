package com.gianmarques001.biblioteca_api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor

public class MensagemDeErro {

    private String path;

    private String method;

    private Integer status;

    private String statusText;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;


    public MensagemDeErro(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public MensagemDeErro(HttpServletRequest request, HttpStatus status, String message, BindingResult results) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErros(results);
    }

    private void addErros(BindingResult results) {
        this.errors = new HashMap<>();
        results.getFieldErrors().forEach(fieldError -> {
            this.errors.put("field", fieldError.getField());
            this.errors.put("message", fieldError.getDefaultMessage());
        });
    }

//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public String getStatusText() {
//        return statusText;
//    }
//
//    public void setStatusText(String statusText) {
//        this.statusText = statusText;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Map<String, String> getErrors() {
//        return errors;
//    }
//
//    public void setErrors(Map<String, String> errors) {
//        this.errors = errors;
//    }
}
