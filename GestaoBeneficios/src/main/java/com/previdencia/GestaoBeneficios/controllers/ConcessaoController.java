package com.previdencia.GestaoBeneficios.controllers;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.previdencia.GestaoBeneficios.dto.ConcessaoPedidoDTO;
import com.previdencia.GestaoBeneficios.dto.ConcessaoRespostaDTO;
import com.previdencia.GestaoBeneficios.models.Concessao;
import com.previdencia.GestaoBeneficios.repository.ConcessaoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.previdencia.GestaoBeneficios.services.ConcessaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller da classe Beneficios
 * @version 1.1
 * @since 1.0
 * @author Leonardo Fachinello Bonetti
 */
@RequestMapping("api/concessao")
@RestController
public class ConcessaoController {

    private final ConcessaoService concessaoService;

    private final ConcessaoRepository concessaoRepository;

    @Autowired
    public ConcessaoController(ConcessaoService concessaoService,
                               ConcessaoRepository concessaoRepository) {
        this.concessaoService = concessaoService;
        this.concessaoRepository = concessaoRepository;
    }

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<String> ConcessaoPost(@RequestBody ConcessaoPedidoDTO concessao){
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(String.valueOf(concessao.getRequisitante()));
            cpfValidator.assertValid(String.valueOf(concessao.getBeneficiado()));
        }
        catch (InvalidStateException e){
            return ResponseEntity.badRequest().body("Erro na validacao do CPF:\n"
                    + e.getMessage()+ "\nInsira um CPF valido");
        }
        return concessaoService.conceder(concessao);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/{uuid}")
    public ResponseEntity<String> DesativarConcessao(@PathVariable String uuid){
        try {
            UUID uuid2 = UUID.fromString(uuid);
            return concessaoService.desativar(uuid2);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("UUID invalido\n"+e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ativos")
    public ResponseEntity<List<ConcessaoRespostaDTO>> ConcessaoGetAllAtivos() {
        List<ConcessaoRespostaDTO> lista= new ArrayList<>();
        if(concessaoRepository.findAllByStatusTrue().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        concessaoRepository.findAllByStatusTrue().forEach(concessao -> lista.add(concessao.transformaDTO()));
        return ResponseEntity.accepted().body(lista);
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<Concessao>> ConcessaoGetAll() {
        if(concessaoRepository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(concessaoRepository.findAll());
    }
}
