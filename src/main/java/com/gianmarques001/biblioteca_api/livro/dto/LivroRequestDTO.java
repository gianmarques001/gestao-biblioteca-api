package com.gianmarques001.biblioteca_api.livro.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.ISBN;

public record LivroRequestDTO(
        @NotBlank String titulo, @NotBlank String autor, @NotBlank String genero, @NotBlank @ISBN String isbn) {
}
