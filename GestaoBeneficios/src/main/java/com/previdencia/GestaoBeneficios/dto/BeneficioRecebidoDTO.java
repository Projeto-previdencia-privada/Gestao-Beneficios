package com.previdencia.GestaoBeneficios.dto;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BeneficioRecebidoDTO {
    private String nome;
    private int valor;
    private int requisitos;
    private boolean individual;

    public Beneficio transformaBeneficio() {
        Beneficio beneficio = new Beneficio();
        beneficio.setNome(nome);
        beneficio.setValor(valor);
        beneficio.setIndividual(individual);
        beneficio.setConcessoes(null);
        return beneficio;
    }
}
