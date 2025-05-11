package com.gianmarques001.biblioteca_api.usuario.repository;

import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import com.gianmarques001.biblioteca_api.usuario.repository.projection.UsuarioDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String username);

    @Query("select u.nome as nome, u.email as email, count(e.id) as emprestimos from Usuario as u left join Emprestimo as e on u.id = e.usuario.id where u.id =:id")
    UsuarioDetailsProjection buscarPorId(Long id);
}
