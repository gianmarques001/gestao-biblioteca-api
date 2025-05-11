package com.gianmarques001.biblioteca_api.common.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MensagemDeEmail {

    private String contato;
    private String assunto;
    private String livro;
    private Double taxa;

    public MensagemDeEmail(String contato, String assunto, String livro) {
        this.contato = contato;
        this.assunto = assunto;
        this.livro = livro;
    }
}
