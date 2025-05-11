package com.gianmarques001.biblioteca_api.livro.dto;

public record LivroDetailsResponseDTO(Long id, String titulo, String autor, String categoria, String isbn,
                                      boolean disponivel) {

}
