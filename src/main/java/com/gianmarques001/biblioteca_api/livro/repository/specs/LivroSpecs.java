package com.gianmarques001.biblioteca_api.livro.repository.specs;

import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.livro.entity.enums.GeneroLivro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> autorLike(String autor) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("autor")), "%" + autor.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);

    }
}
