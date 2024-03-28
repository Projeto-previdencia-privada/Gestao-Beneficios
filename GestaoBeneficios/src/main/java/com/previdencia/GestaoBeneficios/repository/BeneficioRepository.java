
package com.previdencia.GestaoBeneficios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Beneficio;

/**
 * Repositorio de Beneficios
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

    Beneficio findFirstByNome(String nome);
}