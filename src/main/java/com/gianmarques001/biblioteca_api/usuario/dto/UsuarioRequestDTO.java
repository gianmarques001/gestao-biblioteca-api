package com.gianmarques001.biblioteca_api.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotNull String nome, @Email @NotNull String email, @NotNull String senha, @NotNull String permissao) {
}
