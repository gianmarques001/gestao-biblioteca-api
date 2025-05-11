package com.gianmarques001.biblioteca_api.emprestimo.repository;

import com.gianmarques001.biblioteca_api.emprestimo.entity.Emprestimo;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Page<Emprestimo> findAllByUsuario(Usuario usuario, Pageable pageable);
}
