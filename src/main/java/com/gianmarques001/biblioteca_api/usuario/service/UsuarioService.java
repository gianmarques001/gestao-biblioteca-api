package com.gianmarques001.biblioteca_api.usuario.service;

import com.gianmarques001.biblioteca_api.usuario.exception.EmailDuplicadoException;
import com.gianmarques001.biblioteca_api.auth.exception.EntidadeNaoEncontradaException;
import com.gianmarques001.biblioteca_api.usuario.exception.SenhaIncorretaException;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import com.gianmarques001.biblioteca_api.usuario.repository.UsuarioRepository;
import com.gianmarques001.biblioteca_api.usuario.repository.projection.UsuarioDetailsProjection;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        try {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new EmailDuplicadoException("Email já cadastrado");
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    }

    public UsuarioDetailsProjection buscarUsuario(Long id) {
        buscarUsuarioPorId(id);
        return usuarioRepository.buscarPorId(id);
    }

    @Transactional
    public void atualizarUsuario(Long id, String senha, String novaSenha) {
        Usuario usuario = buscarUsuarioPorId(id);

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new SenhaIncorretaException("The password is incorrect.");
        }
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }
}
