package com.previdencia.GestaoBeneficios.models;

import java.time.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * Classe para a criacao da tabela Concessoes e seus atributos
 *
 * @version 1.1
 * @since 1.0
 */
@Entity
@Table(name="Concessao")
public class Concessao {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable=false,length=11)
    private long requisitante;

    @Column(nullable=false,length=11)
    private long beneficiado;

    @Column(nullable=false,length=10)
    private LocalDate data;

    @Column(nullable=false,length=8)
    private double valor;

    @Column(nullable=false)
    private boolean status;

    @ManyToOne
    @JoinColumn(nullable= false)
    private Beneficio beneficio;

    /**
     * Construtor da classe Concessoes
     * @param id
     * @param requisitante
     * @param beneficiado
     * @param data
     * @param valor
     * @param status
     * @param beneficio
     */
    public Concessao(UUID id, long requisitante, long beneficiado, LocalDate data, double valor, boolean status,
                      Beneficio beneficio) {
        this.id = id;
        this.requisitante = requisitante;
        this.beneficiado = beneficiado;
        this.data = data;
        this.valor = valor;
        this.status = status;
        this.beneficio = beneficio;
    }

    public Concessao() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getRequisitante() {
        return requisitante;
    }

    public void setRequisitante(long requisitante) {
        this.requisitante = requisitante;
    }

    public long getBeneficiado() {
        return beneficiado;
    }

    public void setBeneficiado(long beneficiado) {
        this.beneficiado = beneficiado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Beneficio getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

}
