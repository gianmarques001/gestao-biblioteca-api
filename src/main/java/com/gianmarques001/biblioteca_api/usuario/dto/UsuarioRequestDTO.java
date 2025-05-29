package com.gianmarques001.biblioteca_api.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank String nome, @Email @NotBlank String email, @NotBlank String senha, @NotBlank String permissao) {
}
