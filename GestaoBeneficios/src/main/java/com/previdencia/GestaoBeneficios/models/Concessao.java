package com.previdencia.GestaoBeneficios.models;

import java.time.*;
import java.util.UUID;

import com.previdencia.GestaoBeneficios.dto.ConcessaoRespostaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe para a criacao da tabela Concessoes e seus atributos
 *
 * @version 1.1
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Concessao")
public class Concessao {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable=false)
    private UUID uuid;

    @Column(nullable=false,length=11)
    private long requisitante;

    @Column(nullable=false,length=11)
    private long beneficiado;

    @Column(nullable=false,length=10)
    private LocalDate data;

    @Column(nullable=false,length=8)
    private double valor;

    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(nullable= false)
    private Beneficio beneficio;


    public Concessao(UUID uuid, long requisitante, long beneficiado, LocalDate data,
                     double valor, boolean status, Beneficio beneficio) {
        this.uuid = uuid;
        this.requisitante = requisitante;
        this.beneficiado = beneficiado;
        this.data = data;
        this.valor = valor;
        this.status = status;
        this.beneficio = beneficio;
    }

    public ConcessaoRespostaDTO transformaDTO(){
        return new ConcessaoRespostaDTO(uuid,requisitante,beneficiado,
                data,valor,status,beneficio.getNome());
    }

}
