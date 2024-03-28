package com.previdencia.GestaoBeneficios.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Classe para a criacao da tabela Beneficios e seus atributos
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 * 
 */

@Entity
@Table(name="Beneficios")
public class Beneficios implements Serializable{

		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private long id;
		
		@JsonProperty("nome")
		@Column(nullable=false,length=40)
		private String nome;
		
		@JsonProperty("valor_percentual")
		@Column(nullable=false,length=3)
		private int valor;
		
		@JsonProperty("contribuicao_minima")
		@Column(nullable=false,length=4)
		private int requisitos;
		
		@JsonProperty("individual")
		@Column(nullable=false)
		private boolean individual;
		
		@OneToMany(mappedBy = "beneficio")
		@Column(nullable=true,length=4)
		private List<Concessoes> concessoes;
		
		/**
		 * Construtor da classe Beneficios
		 * @param nome
		 * @param valor
		 * @param requisitos
		 * @param individual
		 */

		public Beneficios(String nome, int valor, int requisitos, boolean individual) {
			this.nome = nome;
			this.valor = valor;
			this.requisitos = requisitos;
			this.individual = individual;
		}
		
		public Beneficios() {
			
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public int getValor() {
			return valor;
		}

		public void setValor(int valor) {
			this.valor = valor;
		}

		public int getRequisitos() {
			return requisitos;
		}

		public void setRequisitos(int requisitos) {
			this.requisitos = requisitos;
		}

		public boolean isIndividual() {
			return individual;
		}

		public void setIndividual(boolean individual) {
			this.individual = individual;
		}

		public List<Concessoes> getConcessoes() {
			return concessoes;
		}

		public void setConcessoes(List<Concessoes> concessoes) {
			this.concessoes = concessoes;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

}
