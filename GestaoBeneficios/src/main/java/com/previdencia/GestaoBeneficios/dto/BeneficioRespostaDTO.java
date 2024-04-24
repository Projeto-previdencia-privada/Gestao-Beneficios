package com.previdencia.GestaoBeneficios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficioRespostaDTO {

    private long id;
    private String nome;
    private int valorPercentual;
    private int tempoMinimo;
    private boolean individual;

}
