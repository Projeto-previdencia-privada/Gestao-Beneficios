package com.previdencia.GestaoBeneficios.controllers;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.previdencia.GestaoBeneficios.dto.BeneficioRespostaDTO;
import com.previdencia.GestaoBeneficios.models.Concessao;
import com.previdencia.GestaoBeneficios.repository.ConcessaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.previdencia.GestaoBeneficios.services.ConcessaoService;

import java.util.List;
import java.util.UUID;

/**
 * Controller da classe Beneficios
 * @version 1.1
 * @since 1.0
 * @author Leonardo Fachinello Bonetti
 */
@RequestMapping("api/v2.0/concessao")
@RestController
@Tag(name= "Concessoes",
        description = "Chamadas com relacao a autorizacao e criacao de concessoes")
public class ConcessaoController {
    @Autowired
    private ConcessaoService concessaoService;
    @Autowired
    private ConcessaoRepository concessaoRepository;

    @GetMapping("/soma/{cpf}")
    public ResponseEntity<String> GetSoma(@PathVariable Long cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(String.valueOf(cpf));
        }
        catch (InvalidStateException e){
            return ResponseEntity.badRequest().body("Erro na validacao do CPF:\n"
                    + e.getMessage()+ "\nInsira um CPF valido");
        }
        return concessaoService.somar(cpf);
    }

    @PostMapping
    public ResponseEntity<String> ConcessaoPost(@RequestParam Long cpfRequisitante,
                                                @RequestParam Long beneficio_id,
                                                @RequestParam  Long cpfBeneficiado,
                                                @RequestParam  int op){
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(String.valueOf(cpfBeneficiado));
            cpfValidator.assertValid(String.valueOf(cpfRequisitante));
        }
        catch (InvalidStateException e){
            return ResponseEntity.badRequest().body("Erro na validacao do CPF:\n"
                    + e.getMessage()+ "\nInsira um CPF valido");
        }
        return concessaoService.conceder(cpfRequisitante, beneficio_id, cpfBeneficiado, op);
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<String> DesativarConcessao(@PathVariable String uuid){
        try {
            UUID uuid2 = UUID.fromString(uuid);
            return concessaoService.desativar(uuid2);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("UUID invalido\n"+e.getMessage());
        }
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> ApagarConcessao(@PathVariable String uuid){
        try {
            UUID uuid2 = UUID.fromString(uuid);
            return concessaoService.remover(uuid2);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("UUID invalido\n" + e.getMessage());
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Concessao>> BeneficioGetAllAtivos() {
        if(concessaoRepository.findAllByStatusTrue().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(concessaoRepository.findAllByStatusTrue());
    }

    @GetMapping
    public ResponseEntity<List<Concessao>> BeneficioGetAll() {
        if(concessaoRepository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(concessaoRepository.findAll());
    }
}
