package com.gianmarques001.biblioteca_api.livro.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ISBN;

public record LivroRequestDTO(
        @NotNull String titulo, @NotNull String autor, @NotNull String genero, @NotNull @ISBN String isbn) {
}
