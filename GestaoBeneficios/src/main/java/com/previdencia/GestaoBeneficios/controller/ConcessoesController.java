package com.previdencia.GestaoBeneficios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.previdencia.GestaoBeneficios.models.Beneficios;
import com.previdencia.GestaoBeneficios.services.BeneficioService;
import com.previdencia.GestaoBeneficios.services.ConcessoesService;

@RequestMapping("/concessao")
@RestController
public class ConcessoesController {
	
	@Autowired
	private ConcessoesService concessaoService;
	
	@GetMapping("/soma")
	public double GetSoma(@RequestParam Long id) {
		return concessaoService.somar(id);
	}
	
	@PostMapping("/individual")
	public ResponseEntity<Object> BeneficioPost(@RequestParam Long cpf,
			@RequestParam Long beneficio_id){
        return concessaoService.concederIndividual(cpf, beneficio_id);
    }
	@PostMapping
	public ResponseEntity<Object> BeneficioPost(@RequestParam Long cpfRequisitante,
			@RequestParam Long beneficio_id, @RequestParam  Long cpfBeneficiado){
        return concessaoService.conceder(cpfRequisitante, beneficio_id, cpfBeneficiado);
    }
}

