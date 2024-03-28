package com.previdencia.GestaoBeneficios.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previdencia.GestaoBeneficios.models.Concessoes;
/**
 * Repositorio de Concessoes
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
public interface ConcessaoRepository extends JpaRepository<Concessoes, UUID> {

	 List<Concessoes> findAllByRequisitante(Long requisitante);
	 
}
