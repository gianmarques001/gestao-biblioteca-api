package com.gianmarques001.biblioteca_api.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateRequestDTO(@NotBlank String senha, @NotBlank String novaSenha) {
}
