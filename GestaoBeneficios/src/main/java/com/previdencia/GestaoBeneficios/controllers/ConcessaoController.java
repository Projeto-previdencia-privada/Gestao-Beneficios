package com.previdencia.GestaoBeneficios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.previdencia.GestaoBeneficios.services.ConcessaoService;

import java.util.UUID;

/**
 * Controller da classe Beneficios
 * @version 1.1
 * @since 1.0
 * @author Leonardo Fachinello Bonetti
 */
@RequestMapping("/concessao")
@RestController
public class ConcessaoController {
    @Autowired
    private ConcessaoService concessaoService;

    @GetMapping("/soma")
    public double GetSoma(@RequestParam Long id) {
        return concessaoService.somar(id);
    }

    @PostMapping("/individual")
    public ResponseEntity<String> ConcessaoPost(@RequestParam Long cpf,
                                                @RequestParam Long beneficio_id){
        return concessaoService.concederIndividual(cpf, beneficio_id);
    }
    @PostMapping
    public ResponseEntity<String> ConcessaoPost(@RequestParam Long cpfRequisitante,
                                                @RequestParam Long beneficio_id, @RequestParam  Long cpfBeneficiado){
        return concessaoService.conceder(cpfRequisitante, beneficio_id, cpfBeneficiado);
    }
    @PutMapping
    public ResponseEntity<String> DesativarConcessao(@RequestParam UUID uuid){
        return concessaoService.desativar(uuid);
    }
}
