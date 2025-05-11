package com.gianmarques001.biblioteca_api.livro.dto;

import com.gianmarques001.biblioteca_api.livro.entity.enums.GeneroLivro;

public record LivroResponseDTO(Long id, String titulo, String autor, String isbn,
                               GeneroLivro genero, boolean disponivel) {
}
