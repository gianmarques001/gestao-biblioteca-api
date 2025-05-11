package com.gianmarques001.biblioteca_api.emprestimo.service;

import com.gianmarques001.biblioteca_api.auth.exception.EntidadeNaoEncontradaException;
import com.gianmarques001.biblioteca_api.emprestimo.exception.EmprestimoDuplicadoException;
import com.gianmarques001.biblioteca_api.emprestimo.exception.EmprestimoEncerradoException;
import com.gianmarques001.biblioteca_api.emprestimo.exception.LimiteEstouradoException;
import com.gianmarques001.biblioteca_api.emprestimo.entity.Emprestimo;
import com.gianmarques001.biblioteca_api.emprestimo.repository.EmprestimoRepository;
import com.gianmarques001.biblioteca_api.livro.exception.LivroIndisponivelException;
import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import com.gianmarques001.biblioteca_api.livro.repository.LivroRepository;
import com.gianmarques001.biblioteca_api.taxa.service.TaxaService;
import com.gianmarques001.biblioteca_api.livro.service.LivroService;
import com.gianmarques001.biblioteca_api.notificacao.service.NotificadorService;
import com.gianmarques001.biblioteca_api.usuario.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmprestimoService {

    private final Integer PRAZO_DIAS_PADRAO = 3;

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioService usuarioService;
    private final NotificadorService notificadorService;
    private final TaxaService taxaService;
    private final LivroService livroService;


    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository, UsuarioService usuarioService, NotificadorService notificadorService, TaxaService taxaService, LivroService livroService) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioService = usuarioService;
        this.notificadorService = notificadorService;
        this.taxaService = taxaService;
        this.livroService = livroService;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Emprestimo não encontrado."));

    }

    public Emprestimo realizarEmprestimo(Livro livro, Long idUsuario) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        livroService.buscarLivroPorTitulo(livro.getTitulo());
        if (validarEmprestimo(livro, usuario)) return null;
        LocalDateTime prazoInicio = LocalDateTime.now();
        LocalDateTime prazoFinal = prazoInicio.plusDays(PRAZO_DIAS_PADRAO);
        Emprestimo emprestimo = new Emprestimo(usuario, livro, prazoInicio, prazoFinal);
        usuario.adicionarEmprestimo(emprestimo);
        livro.setDisponivel(false);
        notificadorService.notificarEmprestimo(usuario, livro, false, 0);
        return emprestimoRepository.save(emprestimo);
    }


    public Double encerrarEmprestimo(Emprestimo emprestimo) {

        Emprestimo emprestimoProcurado = buscarEmprestimoPorId(emprestimo.getId());

        if (emprestimoProcurado.isDevolvido()) {
            throw new EmprestimoEncerradoException("Esse emprestimo já está encerrado. Verifique novamente.");
        }

        Usuario usuario = emprestimo.getUsuario();
        Livro livro = emprestimo.getLivro();
        double taxa = taxaService.getCalculoTaxa(emprestimo, usuario.getPermissaoNome());
        usuario.removerEmprestimo(emprestimo);
        livro.setDisponivel(true);
        notificadorService.notificarEmprestimo(usuario, livro, true, taxa);
        notificadorService.notificarReservas(livro);
        livroRepository.save(livro);
        emprestimo.setDevolvido(true);
        emprestimoRepository.save(emprestimo);
        return taxa;
    }

    private boolean validarEmprestimo(Livro livro, Usuario usuario) {

        if (usuario.possuiEmprestimoAtivo(livro)) {
            throw new EmprestimoDuplicadoException("Usuário já possui este livro emprestado.");
        }
        if (usuario.atingiuLimiteEmprestimos()) {
            throw new LimiteEstouradoException("Usuário atingiu o limite de empréstimos permitidos.");
        }
        System.out.println("Passou por pela exceção");
        if (!livro.getDisponivel()) {
            livro.adicionarObservadores(usuario);
            livroRepository.save(livro);
            throw new LivroIndisponivelException("Livro indisponível no momento para empréstimo. Você foi adicionado a lista de para reservar.");
        }
        return false;
    }


    public Page<Emprestimo> listarEmprestimosPorUsuario(Long idUsuario, Integer pagina, Integer tam_pagina) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        Pageable pageRequest = PageRequest.of(pagina, tam_pagina);
        return emprestimoRepository.findAllByUsuario(usuario, pageRequest);
    }


}
