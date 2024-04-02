
package com.previdencia.GestaoBeneficios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Beneficios
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

    Beneficio findFirstByNome(String nome);
}