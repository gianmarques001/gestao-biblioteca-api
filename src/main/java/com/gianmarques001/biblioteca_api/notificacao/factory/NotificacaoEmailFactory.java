package com.gianmarques001.biblioteca_api.notificacao.factory;

import com.gianmarques001.biblioteca_api.common.service.EmailService;
import com.gianmarques001.biblioteca_api.notificacao.interfaces.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoEmailFactory extends NotificacaoFactory {


    private final EmailService emailService;

    public NotificacaoEmailFactory(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public Notificacao criaNotificacao() {
        return emailService;
    }
}
