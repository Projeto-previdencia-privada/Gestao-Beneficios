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
    private int valorPercentual;
    private int tempoMinimo;

    public Beneficio transformaBeneficio() {
        Beneficio beneficio = new Beneficio();
        beneficio.setNome(nome);
        beneficio.setValorPercentual(valorPercentual);
        beneficio.setTempoMinimo(tempoMinimo);
        beneficio.setStatus(true);
        beneficio.setConcessoes(null);
        return beneficio;
    }
}
