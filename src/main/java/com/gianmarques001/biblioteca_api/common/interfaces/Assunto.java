package com.gianmarques001.biblioteca_api.common.interfaces;

import com.gianmarques001.biblioteca_api.notificacao.interfaces.Notificacao;

public interface Assunto {

    void adicionarObservadores(Observador observador);

    void notificar(Notificacao notificacao);

}
