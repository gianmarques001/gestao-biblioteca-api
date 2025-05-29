package com.gianmarques001.biblioteca_api.usuario.entity;

import com.gianmarques001.biblioteca_api.common.interfaces.Observador;
import com.gianmarques001.biblioteca_api.emprestimo.entity.Emprestimo;
import com.gianmarques001.biblioteca_api.permissao.entity.Permissao;
import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "tb_usuarios")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Observador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "permissao_id")
    private Permissao permissao;

    @OneToMany
    private List<Emprestimo> emprestimos;


    public boolean possuiEmprestimoAtivo(Livro livro) {
        return this.emprestimos.stream().anyMatch(emprestimo -> emprestimo.getLivro().equals(livro));
    }


    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    public void removerEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
    }

    public boolean atingiuLimiteEmprestimos() {
        String permissao = this.permissao.getNome();

        if (permissao.equals("ROLE_ALUNO")) {
            return this.emprestimos.size() == 1;
        } else if (permissao.equals("ROLE_PROFESSOR")) {
            return this.emprestimos.size() == 2;
        }
        return false;
    }

    public String getPermissaoNome() {
        return this.permissao.getNome();
    }

    @Override
    public void atualizar() {

    }

}
