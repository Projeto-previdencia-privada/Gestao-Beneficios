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
@RequiredArgsConstructor
@Entity
@Table(name="Beneficio")
public class Beneficio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @JsonProperty("nome")
    @Column(nullable=false,length=40)
    @NonNull
    private String nome;

    @JsonProperty("valor_percentual")
    @Column(nullable=false,length=3)
    @NonNull
    private int valor;

    @JsonProperty("contribuicao_minima")
    @Column(nullable=false,length=4)
    @NonNull
    private int requisitos;

    @JsonProperty("individual")
    @Column(nullable=false)
    @NonNull
    private boolean individual;

    @OneToMany(mappedBy = "beneficio")
    @Column(nullable=true,length=4)
    private List<Concessao> concessoes;

    public BeneficioRespostaDTO transformaDTO(){
        return new BeneficioRespostaDTO(id,nome,
                valor, requisitos, individual);
    }
}

