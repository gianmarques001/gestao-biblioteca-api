package com.gianmarques001.biblioteca_api.livro.service;

import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.livro.entity.enums.GeneroLivro;
import com.gianmarques001.biblioteca_api.auth.exception.EntidadeNaoEncontradaException;
import com.gianmarques001.biblioteca_api.livro.exception.IsbnDuplicadoException;
import com.gianmarques001.biblioteca_api.livro.exception.LivroDuplicadoException;
import com.gianmarques001.biblioteca_api.livro.repository.LivroRepository;
import com.gianmarques001.biblioteca_api.livro.repository.specs.LivroSpecs;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvarLivro(Livro livro) {

        Optional<Livro> optionalLivroTitulo = livroRepository.findByTitulo(livro.getTitulo());
        Optional<Livro> optionalLivroIsbn = livroRepository.findByIsbn(livro.getIsbn());
        if (optionalLivroTitulo.isPresent()) {
            throw new LivroDuplicadoException("Livro já existente.");
        }
        if (optionalLivroIsbn.isPresent()) {
            throw new IsbnDuplicadoException("ISBN já existente.");
        }
        livro.setDisponivel(true);
        return livroRepository.save(livro);
    }

    public Page<Livro> listarLivros(String titulo, String autor, GeneroLivro genero, Integer pagina, Integer tam_pagina) {

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (titulo != null && !titulo.isEmpty()) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if (autor != null) {
            specs = specs.and(LivroSpecs.autorLike(autor));
        }
        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        Pageable pageRequest = PageRequest.of(pagina, tam_pagina);

        return livroRepository.findAll(specs, pageRequest);
    }

    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Livro não encontrado"));
    }

    public void buscarLivroPorTitulo(String titulo) {
        livroRepository.findByTitulo(titulo).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }
}
