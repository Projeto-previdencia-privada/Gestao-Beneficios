package com.previdencia.GestaoBeneficios.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Concessao;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Concessoes
 *
 * @version 2.0
 * @since 1.0
 */
@Repository
public interface ConcessaoRepository extends JpaRepository<Concessao, Long> {

    List<Concessao> findAllByRequisitante(Long requisitante);
    List<Concessao> findAllByStatusTrue();
    Concessao findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);

}
