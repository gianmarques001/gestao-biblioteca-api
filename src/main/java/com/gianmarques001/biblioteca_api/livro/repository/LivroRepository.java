package com.gianmarques001.biblioteca_api.livro.repository;

import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {

    Optional<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbn(String isbn);
}
