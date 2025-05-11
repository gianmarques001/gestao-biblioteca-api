package com.gianmarques001.biblioteca_api.emprestimo.entity;


import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_emprestimos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Column(name = "prazo_inicial")
    private LocalDateTime prazoInicio;

    @Column(name = "prazo_final")
    private LocalDateTime prazoFinal;

    @Column(name = "devolvido")
    private boolean devolvido;

    public Emprestimo(Usuario usuario, Livro livro, LocalDateTime prazoInicio, LocalDateTime prazoFinal) {
        this.usuario = usuario;
        this.livro = livro;
        this.prazoInicio = prazoInicio;
        this.prazoFinal = prazoFinal;
    }
}
