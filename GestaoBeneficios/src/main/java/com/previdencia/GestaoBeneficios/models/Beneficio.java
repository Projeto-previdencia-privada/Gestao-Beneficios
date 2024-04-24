package com.previdencia.GestaoBeneficios.models;
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
@Table(name="Beneficio")
public class Beneficio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(nullable=false,length=40)
    @NotNull(message= "O nome nao deve ser nulo")
    private String nome;

    @Column(nullable=false,length=3)
    @NotNull(message= "O valor nao deve ser nulo")
    private int valorPercentual;

    @Column(nullable=false,length=4)
    @NotNull(message= "O tempo nao deve ser nulo")
    private int tempoMinimo;

    @Column(nullable=false)
    private boolean individual;

    @OneToMany(mappedBy = "beneficio")
    @Column(nullable=true,length=4)
    private List<Concessao> concessoes;

    public BeneficioRespostaDTO transformaDTO(){
        return new BeneficioRespostaDTO(id,nome,
                valorPercentual, tempoMinimo, individual);
    }
}

