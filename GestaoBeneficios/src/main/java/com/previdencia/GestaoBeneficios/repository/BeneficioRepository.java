
package com.previdencia.GestaoBeneficios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de Beneficios
 * @author Leonardo Fachinello Bonetti
 * @version 2.0
 * @since 1.0
 *
 */
@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {
    Beneficio findByNome(String nome);
    List<Beneficio> findByNomeContaining(String nome);
    List<Beneficio> findByNomeContainingIgnoreCase(String nome);
    boolean existsByNome(String nome);
}