package com.previdencia.GestaoBeneficios.controllers;

import com.previdencia.GestaoBeneficios.dto.BeneficioRecebidoDTO;
import com.previdencia.GestaoBeneficios.dto.BeneficioRespostaDTO;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.previdencia.GestaoBeneficios.services.BeneficioService;

import java.util.*;

/**
 * Controller da classe Beneficios
 * @version 2.0
 * @since 1.0
 * @author Leonardo Fachinello Bonetti
 */
@RequestMapping("api/beneficio")
@RestController
public class BeneficioController {
    private final BeneficioService beneficioService;

    private final BeneficioRepository beneficioRepository;

    @Autowired
    public BeneficioController(BeneficioService beneficioService,
                               BeneficioRepository beneficioRepository) {
        this.beneficioService = beneficioService;
        this.beneficioRepository = beneficioRepository;
    }

    /**
     * Recebe as chamadas POST da API e chama o metodo de adicionar beneficio
     * @param beneficio
     *
     */
    @CrossOrigin(origins = "http://localhost:5300")
    @PostMapping
    public ResponseEntity<String> BeneficioPost(@RequestBody BeneficioRecebidoDTO beneficio)
    {
        if(beneficio.getValorPercentual()<=0 ||
                beneficio.getNome().isBlank() ||
                beneficio.getTempoMinimo()<0){
            return ResponseEntity.badRequest().body("Beneficio com valores improprios");
        }
        return beneficioService.adicionar(beneficio);
    }

    /**
     * Recebe as chamadas PATCH da API e chama o metodo de deletar beneficios
     *
     * @param id
     * @return
     */
    @CrossOrigin(origins = "http://localhost:5300")
    @PatchMapping("/{id}")
    public ResponseEntity<String> BeneficioDesativar(@PathVariable Long id) {

        return beneficioService.desativar(id);
    }
    /**
     * Recebe as chamadas PUT da API e chama o metodo de alteracao de beneficios
     *
     * @param id
     * @param beneficio
     */
    @CrossOrigin(origins = "http://localhost:5300")
    @PutMapping("/{id}")
    public ResponseEntity<String> BeneficioPut(@PathVariable Long id,
                                               @RequestBody BeneficioRecebidoDTO beneficio){
        if(beneficio.getValorPercentual()<=0 ||
                beneficio.getNome().isBlank() ||
                beneficio.getTempoMinimo()<0){
            return ResponseEntity.badRequest().body("Beneficio com valores improprios");
        }
        return beneficioService.alterar(id, beneficio);
    }

    @CrossOrigin(origins = "http://localhost:5300")
    @GetMapping
    public ResponseEntity<List<BeneficioRespostaDTO>> BeneficioGetAll() {
        if(beneficioRepository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(beneficioService.listarBeneficios());
    }

    @CrossOrigin(origins = "http://localhost:5300")
    @GetMapping("/{id}")
    public ResponseEntity<BeneficioRespostaDTO> BeneficioGetById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .accepted().body(beneficioRepository.
                            findById(id).orElseThrow().transformaDTO());
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
