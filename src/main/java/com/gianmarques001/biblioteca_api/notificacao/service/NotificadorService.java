package com.gianmarques001.biblioteca_api.notificacao.service;

import com.gianmarques001.biblioteca_api.common.model.MensagemDeEmail;
import com.gianmarques001.biblioteca_api.common.interfaces.Observador;
import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.notificacao.interfaces.Notificacao;
import com.gianmarques001.biblioteca_api.notificacao.factory.NotificacaoFactory;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gianmarques001.biblioteca_api.common.service.EmailService.*;

@Service
public class NotificadorService {

    private final NotificacaoFactory notificacaoFactory;

    @Autowired
    public NotificadorService(NotificacaoFactory notificacaoFactory) {
        this.notificacaoFactory = notificacaoFactory;
    }

    public void notificarReservas(Livro livro) {
        Notificacao notificacao = notificacaoFactory.criaNotificacao();

        for (Observador observador : livro.getListaObservadores()) {
            if (observador instanceof Usuario usuario) {
                notificacao.notificar(new MensagemDeEmail(usuario.getEmail(), ASSUNTO_LIVRO_DISPONIVEL, livro.getTitulo()));
            }
        }
        livro.notificar(notificacao);
    }


    public void notificarEmprestimo(Usuario usuario, Livro livro, boolean devolvido, double taxa) {
        Notificacao notificacao = notificacaoFactory.criaNotificacao();
        String assunto = devolvido ? ASSUNTO_EMPRESTIMO_ENCERRADO : ASSUNTO_EMPRESTIMO_FEITO;
        MensagemDeEmail mensagemDeEmail = new MensagemDeEmail(usuario.getEmail(), assunto, livro.getTitulo(), taxa);
        notificacao.notificar(mensagemDeEmail);
    }
}

