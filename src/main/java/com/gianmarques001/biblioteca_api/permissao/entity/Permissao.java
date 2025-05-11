package com.gianmarques001.biblioteca_api.permissao.entity;

import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_permissoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;


    @OneToMany(mappedBy = "permissao")
    private Set<Usuario> usuario = new HashSet<>();


}
