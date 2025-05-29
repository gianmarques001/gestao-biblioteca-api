package com.gianmarques001.biblioteca_api.permissao.repository;

import com.gianmarques001.biblioteca_api.permissao.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Permissao findByNome(String nome);
}
