package com.gianmarques001.biblioteca_api.emprestimo.dto;

import jakarta.validation.constraints.NotNull;

public record EmprestimoRequestDTO(@NotNull Long idLivro) {
}
