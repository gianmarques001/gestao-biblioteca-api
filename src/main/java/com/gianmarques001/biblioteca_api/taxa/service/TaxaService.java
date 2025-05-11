package com.gianmarques001.biblioteca_api.taxa.service;

import com.gianmarques001.biblioteca_api.emprestimo.entity.Emprestimo;
import com.gianmarques001.biblioteca_api.taxa.interfaces.Taxa;
import com.gianmarques001.biblioteca_api.taxa.factory.TaxaFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TaxaService {

    private final TaxaFactory taxaFactory;

    public TaxaService(TaxaFactory taxaFactory) {
        this.taxaFactory = taxaFactory;
    }

    public double getCalculoTaxa(Emprestimo emprestimo, String permissao) {
        LocalDateTime dataVencimentoOriginal = emprestimo.getPrazoFinal();
        LocalDateTime dataVencimentoDevolução = LocalDateTime.now().plusDays(20);
        Duration duracao = Duration.between(dataVencimentoOriginal, dataVencimentoDevolução);

        long dias = duracao.toDays();
        if (dias < 0) return 0;
        Taxa taxa = taxaFactory.getStrategy(permissao);
        return taxa.calcularTaxa(dias);
    }
}
