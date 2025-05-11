package com.gianmarques001.biblioteca_api.usuario.dto;

import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateRequestDTO(@NotNull String senha, @NotNull String novaSenha) {
}
