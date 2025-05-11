package com.gianmarques001.biblioteca_api.emprestimo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EmprestimoDetailsResponseDTO(String titulo,
                                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime prazoInicio,
                                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime prazoFinal,
                                           boolean devolvido) {
}
