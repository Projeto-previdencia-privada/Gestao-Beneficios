package com.previdencia.GestaoBeneficios.repository;
import java.util.List;
import java.util.UUID;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Concessao;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Concessoes
 *
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ConcessaoRepository extends JpaRepository<Concessao, UUID> {

    List<Concessao> findAllByRequisitante(Long requisitante);
    Concessao findFirstByRequisitante(Long requisitante);

}
