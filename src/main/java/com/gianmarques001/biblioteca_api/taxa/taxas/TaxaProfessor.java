package com.gianmarques001.biblioteca_api.taxa.taxas;

import com.gianmarques001.biblioteca_api.taxa.interfaces.Taxa;
import org.springframework.stereotype.Component;

@Component("ROLE_PROFESSOR")
public class TaxaProfessor implements Taxa {
    @Override
    public double calcularTaxa(long dias) {
        return dias * 10.50;

    }
}
