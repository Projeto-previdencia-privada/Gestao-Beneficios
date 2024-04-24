package com.previdencia.GestaoBeneficios.dto;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BeneficioRecebidoDTO {
    @NotEmpty(message= "O nome nao deve ser nulo")
    @Size(min=1, max=30, message = "O nome precisa ter entre 1 a 30 caracteres")
    private String nome;

    @NotNull(message= "O valor percentual nao deve ser nulo")
    @Max(value = 100, message = "Valor nao pode superar a cem por cento da contribuicao do cpf")
    @Min(value = 0, message = "Valor precisa ser significativo")
    private int valorPercentual;

    @NotNull(message= "O tempo nao deve ser nulo")
    @Min(value = 0, message = "O tempo nao deve ser nulo")
    @Max(value = 1200, message = "Tempo de contribuicao nao pode passar de 1200 meses")
    private int tempoMinimo;

    @NotEmpty(message = "O beneficio precisa ser classificado")
    private boolean individual;

    public Beneficio transformaBeneficio() {
        Beneficio beneficio = new Beneficio();
        beneficio.setNome(nome);
        beneficio.setValorPercentual(valorPercentual);
        beneficio.setTempoMinimo(tempoMinimo);
        beneficio.setIndividual(individual);
        beneficio.setConcessoes(null);
        return beneficio;
    }
}
