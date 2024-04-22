package com.previdencia.GestaoBeneficios.controllers;

import com.previdencia.GestaoBeneficios.dto.BeneficioRecebidoDTO;
import com.previdencia.GestaoBeneficios.dto.BeneficioRespostaDTO;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.services.BeneficioService;

import java.util.*;

/**
 * Controller da classe Beneficios
 * @version 2.0
 * @since 1.0
 * @author Leonardo Fachinello Bonetti
 */
@RequestMapping("api/v2.0/beneficios")
@RestController
@Tag(name= "Beneficios",
        description = "Chamadas com relacao a criacao e manipulacao de beneficios")
public class BeneficioController {
    @Autowired
    private BeneficioService beneficioService;
    @Autowired
    private BeneficioRepository beneficioRepository;

    /**
     * Recebe as chamadas POST da API e chama o metodo de adicionar beneficio
     * @param beneficio
     *
     */
    @PostMapping
    @Operation(tags = "Beneficios",
    summary = "Adiciona um beneficio no sistema",
    description = "Insere um beneficio no sistema")
    public ResponseEntity<String> BeneficioPost(@Parameter(description = "Beneficio a ser criado") @RequestBody BeneficioRecebidoDTO beneficio)
    {
        return beneficioService.adicionar(beneficio);
    }

    /**
     * Recebe as chamadas DELETE da API e chama o metodo de deletar beneficios
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(tags = "Beneficios",
            summary = "Remove um beneficio no sistema")
    public ResponseEntity<String> BeneficioDelete(@RequestParam Long id) {
        return beneficioService.remover(id);
    }

    /**
     * Recebe as chamadas PUT da API e chama o metodo de alteracao de beneficios
     *
     * @param id
     * @param beneficio
     * @return
     */
    @PutMapping
    @Operation(tags = "Beneficios",
            summary = "Altera um beneficio no sistema")
    public ResponseEntity<String> BeneficioPut(@RequestParam Long id,
                                               @RequestBody BeneficioRecebidoDTO beneficio){
        return beneficioService.alterar(id, beneficio);
    }

    @GetMapping
    @Operation(tags = "Beneficios",
            summary = "Obtem todos os beneficios no sistema")
    public ResponseEntity<List<BeneficioRespostaDTO>> BeneficioGetAll() {
        if(beneficioRepository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(beneficioService.listarBeneficios(1));
    }

    @GetMapping("/individual")
    @Operation(tags = "Beneficios",
            summary = "Obtem todos os beneficios individuais no sistema")
    public ResponseEntity<List<BeneficioRespostaDTO>> BeneficioGetAllIndividual() {
        if(beneficioRepository.findAllByIndividualIsTrue().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(beneficioService.listarBeneficios(2));
    }

    @GetMapping("/naoindividual")
    @Operation(tags = "Beneficios",
            summary = "Obtem todos os beneficios nao individuais no sistema")
    public ResponseEntity<List<BeneficioRespostaDTO>> BeneficioGetAllNaoIndividual() {
        if(beneficioRepository.findAllByIndividualIsFalse().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(beneficioService.listarBeneficios(3));
    }

    @GetMapping("/{id}")
    @Operation(tags = "Beneficios",
            summary = "Obtem um beneficio no sistema pelo id")
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
