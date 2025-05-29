package com.gianmarques001.biblioteca_api.livro.entity;

import com.gianmarques001.biblioteca_api.notificacao.interfaces.Notificacao;
import com.gianmarques001.biblioteca_api.common.interfaces.Observador;
import com.gianmarques001.biblioteca_api.common.interfaces.Assunto;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import com.gianmarques001.biblioteca_api.livro.entity.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_livros")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Livro implements Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroLivro genero;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private boolean disponivel;

    @ManyToMany
    @JoinTable(
            name = "tb_livro_reservas",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    public List<Usuario> listaObservadores = new ArrayList<>();

    public boolean getDisponivel() {
        return this.disponivel;
    }


    @Override
    public void adicionarObservadores(Observador observador) {
        Usuario usuario = (Usuario) observador;
        if (!listaObservadores.contains(usuario)) {
            listaObservadores.add(usuario);

        } else {
            System.out.println("Usuário já está na lista de reservas.");
        }
    }

    @Override
    public void notificar(Notificacao notificacao) {
        for (Observador observador : listaObservadores) {
            observador.atualizar();
        }
        listaObservadores.clear();
    }
}
