package com.gianmarques001.biblioteca_api.notificacao.interfaces;

import com.gianmarques001.biblioteca_api.common.model.MensagemDeEmail;

public interface Notificacao {

    void notificar(MensagemDeEmail mensagemDeEmail);
}
