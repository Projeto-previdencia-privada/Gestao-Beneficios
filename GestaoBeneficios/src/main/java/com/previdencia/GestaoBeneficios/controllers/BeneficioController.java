package com.previdencia.GestaoBeneficios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.services.BeneficioService;

/**
 * Controller da classe Beneficios
 * @version 1.1
 * @since 1.0
 * @author Leonardo Fachinello Bonetti
 */
@RequestMapping("/beneficios")
@RestController
public class BeneficioController {
    @Autowired
    private BeneficioService beneficioService;

    /**
     * Recebe as chamadas POST da API e chama o metodo de adicionar beneficio
     * @param beneficio
     *
     */
    @PostMapping
    public ResponseEntity<Object> BeneficioPost(@RequestBody Beneficio beneficio)
    {
        return beneficioService.adicionar(beneficio);
    }

    /**
     * Recebe as chamadas DELETE da API e chama o metodo de deletar beneficios
     * @param id
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Object> BeneficioDelete(@RequestParam Long id) {
        return beneficioService.remover(id);
    }

    /**
     * Recebe as chamadas PUT da API e chama o metodo de alteracao de beneficios
     * @param id
     * @param beneficio
     * @return
     */
    @PutMapping
    public ResponseEntity<Object> BeneficioPut(@RequestParam Long id,
                                               @RequestBody Beneficio beneficio){
        return beneficioService.alterar(id, beneficio);
    }
}
