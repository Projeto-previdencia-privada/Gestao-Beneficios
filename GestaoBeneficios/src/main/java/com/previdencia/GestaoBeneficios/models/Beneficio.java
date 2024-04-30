package com.previdencia.GestaoBeneficios.models;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.previdencia.GestaoBeneficios.dto.BeneficioRespostaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Classe para a criacao da tabela Beneficios e seus atributos
 *
 * @version 1.1
 * @since 1.0
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Beneficio{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(nullable=false,length=40)
    private String nome;

    @Column(nullable=false,length=3)
    private int valorPercentual;

    @Column(nullable=false,length=4)
    private int tempoMinimo;

    @Column(nullable=false)
    private boolean individual;

    @OneToMany(mappedBy = "beneficio")
    @Column(nullable=true)
    private List<Concessao> concessoes;

    public BeneficioRespostaDTO transformaDTO(){
        return new BeneficioRespostaDTO(id,nome,
                valorPercentual, tempoMinimo, individual);
    }
}

