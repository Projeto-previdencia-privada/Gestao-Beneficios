package com.previdencia.GestaoBeneficios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Beneficios;

/**
 * Repositorio de Beneficios
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
public interface BeneficioRepository extends JpaRepository<Beneficios, Long> {

	Beneficios findFirstByNome(String nome);
}
