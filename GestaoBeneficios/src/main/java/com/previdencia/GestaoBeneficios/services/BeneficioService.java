package com.previdencia.GestaoBeneficios.services;

import com.previdencia.GestaoBeneficios.dto.BeneficioRecebidoDTO;
import com.previdencia.GestaoBeneficios.dto.BeneficioRespostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final BeneficioRepository beneficioRepository;

    @Autowired
    public BeneficioService(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    /**
     * Metodo que recebe o beneficio e o insere no banco de dados
     * @param beneficioDTO beneficio a ser inserido
     */
    public ResponseEntity<String> adicionar(BeneficioRecebidoDTO beneficioDTO) {
        List<Beneficio> lista = beneficioRepository.findAll();

        for (Beneficio b : lista) {
            if (b.getNome().trim().equalsIgnoreCase(beneficioDTO.getNome().trim())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O Beneficio ja esta cadastrado no sistema\n" +
                        "ID : " +b.getId()+ "\nNome : "+b.getNome()+"\n");
            }
        }
        Beneficio beneficio = beneficioDTO.transformaBeneficio();
        beneficioRepository.save(beneficio);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("ID : " + beneficio.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> desativar(Long id) {
        if(!beneficioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Beneficio com ID "+id+" nao encontrado\n");
        }

        Beneficio beneficio = beneficioRepository.getReferenceById(id);
        beneficio.setStatus(false);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Metodo que altera um benefício ja inserido no banco de dados
     * @param id id do beneficio antigo
     * @param newBeneficio beneficio que ira substituir o antigo
     */
    public ResponseEntity<String> alterar(Long id, BeneficioRecebidoDTO newBeneficio) {
        List<Beneficio> lista = beneficioRepository.findAll();
        if (!beneficioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Beneficio com ID "+id+" nao encontrado\n");
        }

        for (Beneficio b : lista) {
            if (b.getNome().trim().equalsIgnoreCase(newBeneficio.getNome().trim())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O Beneficio ja esta cadastrado no sistema\n" +
                                "ID : "+b.getId()+ "\nNome : "+b.getNome()+"\n");
            }
        }


        Beneficio oldBeneficio = beneficioRepository.getReferenceById(id);
        oldBeneficio.setNome(newBeneficio.getNome());
        oldBeneficio.setValorPercentual(newBeneficio.getValorPercentual());
        oldBeneficio.setTempoMinimo(newBeneficio.getTempoMinimo());
        beneficioRepository.saveAndFlush(oldBeneficio);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Beneficio alterado com sucesso!\n");
    }

    public List<BeneficioRespostaDTO> listarBeneficios(){
        List<BeneficioRespostaDTO> beneficioRespostaDTOList = new ArrayList<>();
        List<Beneficio> beneficioList = beneficioRepository.findAll();

        for(Beneficio beneficio : beneficioList){
            beneficioRespostaDTOList.add(beneficio.transformaDTO());
        }
        return beneficioRespostaDTOList;
    }
}
