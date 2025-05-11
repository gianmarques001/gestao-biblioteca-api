package com.gianmarques001.biblioteca_api.taxa.factory;

import com.gianmarques001.biblioteca_api.taxa.exception.TaxaException;
import com.gianmarques001.biblioteca_api.taxa.interfaces.Taxa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaxaFactory {

    private final Map<String, Taxa> estrategiaMap;

    @Autowired
    public TaxaFactory(Map<String, Taxa> estrategias) {
        this.estrategiaMap = estrategias;
    }

    public Taxa getStrategy(String permissao) {
        Taxa strategy = estrategiaMap.get(permissao.toUpperCase());

        if (strategy == null) {
            throw new TaxaException("Nenhuma taxa encontrada para a permissão: " + permissao);
        }

        return strategy;
    }
}
