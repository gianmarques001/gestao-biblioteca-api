package com.gianmarques001.biblioteca_api.auth.dto;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(@NotNull String email, @NotNull String senha) {
}
