package com.gianmarques001.biblioteca_api.auth.entity;

import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthDetails implements UserDetails {

    private Long id;
    private String email;
    private String senha;
    private String permissao;


    public AuthDetails(Usuario usuario) {
        super();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.permissao = usuario.getPermissao().getNome();
        this.id = usuario.getId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.permissao));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
