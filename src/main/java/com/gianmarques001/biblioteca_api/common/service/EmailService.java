package com.gianmarques001.biblioteca_api.common.service;

import com.gianmarques001.biblioteca_api.common.model.MensagemDeEmail;
import com.gianmarques001.biblioteca_api.notificacao.interfaces.Notificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements Notificacao {

    @Autowired
    private JavaMailSender mailSender;

    public static final String ASSUNTO_LIVRO_DISPONIVEL = "LIVRO DISPONIVEL";
    public static final String ASSUNTO_EMPRESTIMO_FEITO = "EMPRESTIMO FEITO";
    public static final String ASSUNTO_EMPRESTIMO_ENCERRADO = "EMPRESTIMO ENCERRADO";

    @Async
    @Override
    public void notificar(MensagemDeEmail mensagemDeEmail) {
        if (mensagemDeEmail.getAssunto().equals(ASSUNTO_LIVRO_DISPONIVEL)) {
            enviarEmailDeReserva(mensagemDeEmail.getContato(), mensagemDeEmail.getLivro());
        } else if (mensagemDeEmail.getAssunto().equals(ASSUNTO_EMPRESTIMO_FEITO)) {
            enviarEmailDeEmprestimoRealizado(mensagemDeEmail.getContato(), mensagemDeEmail.getLivro());
        } else {
            enviarEmailDeEmprestimoEncerrado(mensagemDeEmail.getContato(), mensagemDeEmail.getLivro(), mensagemDeEmail.getTaxa());
        }
    }

    private void enviarEmailDeEmprestimoRealizado(String destinatario, String tituloLivro) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(ASSUNTO_EMPRESTIMO_FEITO + " " + tituloLivro);
        mensagem.setText("Olá " + destinatario + " você pegou o livro " + tituloLivro);
        mailSender.send(mensagem);
    }

    private void enviarEmailDeEmprestimoEncerrado(String destinatario, String tituloLivro, double taxa) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(ASSUNTO_EMPRESTIMO_ENCERRADO + " " + tituloLivro);
        mensagem.setText("Olá " + destinatario + " você devolveu o livro " + tituloLivro + "\nVocê tem a seguinte taxa para pagar R$ " + taxa);
        mailSender.send(mensagem);
    }

    private void enviarEmailDeReserva(String destinatario, String tituloLivro) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(ASSUNTO_LIVRO_DISPONIVEL + " " + tituloLivro);
        mensagem.setText("Olá " + destinatario + ", o livro que você fez a reserva agora está disponível!");
        mailSender.send(mensagem);
    }
}
