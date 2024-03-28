package com.previdencia.GestaoBeneficios.repository;
import java.util.List;
import java.util.UUID;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Concessao;
/**
 * Repositorio de Concessoes
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
public interface ConcessaoRepository extends JpaRepository<Concessao, UUID> {

    List<Concessao> findAllByRequisitante(Long requisitante);

}
