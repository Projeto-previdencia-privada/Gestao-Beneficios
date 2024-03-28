package com.previdencia.GestaoBeneficios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.previdencia.GestaoBeneficios.models.Beneficios;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;

/**
 * Servico da classe beneficio
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
@Service
public class BeneficioService {

	@Autowired
	private final BeneficioRepository beneficioRepository;
	
	public BeneficioService(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }
	
	/**
	 * Metodo que recebe o beneficio e o insere no banco de dados
	 * @param beneficio
	 * @return Http status 201
	 */
    public ResponseEntity<Object> adicionar(Beneficios beneficio) {
    	beneficioRepository.save(beneficio);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    /**
     * Metodo que recebe um beneficio a ser excluido do banco de dados
     * @param id
     * @return Http status 201
     */
    public ResponseEntity<Object> remover(Long id) {
    	beneficioRepository.deleteById(id);
    	return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    /**
     * Metodo que altera um beneficio ja inserido no banco de dados
     * @param id
     * @param newBeneficio
     * @return Http status 201
     * @return Http status 404
     */
    public ResponseEntity<Object> alterar(Long id, Beneficios newBeneficio) {
    	if (beneficioRepository.existsById(id)) {
    		Beneficios oldBeneficio = beneficioRepository.getReferenceById(id);
    		oldBeneficio.setNome(newBeneficio.getNome());
    		oldBeneficio.setValor(newBeneficio.getValor());
    		oldBeneficio.setRequisitos(newBeneficio.getRequisitos());
    		oldBeneficio.setIndividual(newBeneficio.isIndividual());
    		beneficioRepository.saveAndFlush(oldBeneficio);
    		return new ResponseEntity<>(HttpStatus.ACCEPTED);
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
