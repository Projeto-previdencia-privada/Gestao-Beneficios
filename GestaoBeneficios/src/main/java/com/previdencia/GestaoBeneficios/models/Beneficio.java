package com.previdencia.GestaoBeneficios.models;
import java.util.List;

import com.previdencia.GestaoBeneficios.dto.BeneficioRespostaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column
    private boolean status;

    @OneToMany(mappedBy = "beneficio")
    @Column(nullable=true)
    private List<Concessao> concessoes;

    public BeneficioRespostaDTO transformaDTO(){
        return new BeneficioRespostaDTO(id,nome,
                valorPercentual, tempoMinimo, status);
    }
}

