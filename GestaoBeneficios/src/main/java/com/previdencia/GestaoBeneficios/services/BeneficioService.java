package com.previdencia.GestaoBeneficios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;

import java.util.List;

/**
 * Servico da classe beneficio
 * @author Leonardo Fachinello Bonetti
 * @version 1.1
 * @since 1.0
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
     * @param beneficio beneficio a ser inserido
     */
    public ResponseEntity<String> adicionar(Beneficio beneficio) {
        List<Beneficio> lista = beneficioRepository.findAll();

        for (Beneficio b : lista) {
            if (b.getNome().trim().equalsIgnoreCase(beneficio.getNome().trim())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O Beneficio ja esta cadastrado no sistema\n" +
                        "ID : " + beneficio.getId()+"\nNome : "+beneficio.getNome()+"\n");
            }
        }

        beneficioRepository.save(beneficio);
        return ResponseEntity.status(HttpStatus.CREATED).body("Beneficio adicionado com sucesso!\n" +
                "ID : " + beneficio.getId()+"\nNome : "+beneficio.getNome()+"\n");
    }

    /**
     * Metodo que recebe um beneficio a ser excluido do banco de dados
     * @param id
     * @return Http status 201
     */
    public ResponseEntity<String> remover(Long id) {
        beneficioRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Beneficio removido com sucesso!\n");
    }

    /**
     * Metodo que altera um beneficio ja inserido no banco de dados
     * @param id
     * @param newBeneficio
     * @return Http status 201
     * @return Http status 404
     */
    public ResponseEntity<String> alterar(Long id, Beneficio newBeneficio) {
        if (!beneficioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Beneficio com ID "+id+" nao encontrado\n");
        }

        Beneficio oldBeneficio = beneficioRepository.getReferenceById(id);
        oldBeneficio.setNome(newBeneficio.getNome());
        oldBeneficio.setValor(newBeneficio.getValor());
        oldBeneficio.setRequisitos(newBeneficio.getRequisitos());
        oldBeneficio.setIndividual(newBeneficio.isIndividual());
        beneficioRepository.saveAndFlush(oldBeneficio);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Beneficio alterado com sucesso!\n");
    }
}
