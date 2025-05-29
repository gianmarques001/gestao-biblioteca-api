package com.gianmarques001.biblioteca_api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank String email, @NotBlank String senha) {
}
