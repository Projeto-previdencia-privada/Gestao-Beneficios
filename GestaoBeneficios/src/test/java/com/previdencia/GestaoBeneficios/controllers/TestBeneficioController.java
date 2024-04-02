package com.previdencia.GestaoBeneficios.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.previdencia.GestaoBeneficios.controllers.BeneficioController;
import com.previdencia.GestaoBeneficios.services.BeneficioService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
public class TestBeneficioController {
    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private BeneficioService beneficioService;

    @Autowired
    private BeneficioController beneficioController;


    @Test
    public void testPostBeneficio() {

        Beneficio beneficio = beneficio = new Beneficio(
                "Auxilio",
                50,
                2,
                true
        );

        ResponseEntity<Object> resposta= beneficioController.BeneficioPost(beneficio);

        assertThat(beneficioRepository.findFirstByNome("Auxilio").getId()).isGreaterThan(0);
        assertThat(resposta).isEqualTo(new ResponseEntity<>(HttpStatus.CREATED));


    }

    @Test
    public void testDeleteBeneficio() {

        Beneficio beneficio =  new Beneficio(
                "Auxilio",
                50,
                2,
                true
        );

        beneficioController.BeneficioPost(beneficio);
        ResponseEntity<Object> resposta=beneficioController.BeneficioDelete(beneficioRepository.findFirstByNome("Auxilio").getId());

        assertThat(resposta).isEqualTo(new ResponseEntity<>(HttpStatus.ACCEPTED));
    }

    @Test
    public void testPutBeneficio() {

        Beneficio beneficio = new Beneficio(
                "Auxilio",
                50,
                2,
                true
        );
        Beneficio beneficio2 = new Beneficio(
                "Auxilio2",
                51,
                1,
                false
        );
        beneficioController.BeneficioPost(beneficio);
        beneficioController.BeneficioPost(beneficio2);
        ResponseEntity<Object> resposta=beneficioController.BeneficioPut(beneficioRepository.findFirstByNome("Auxilio").getId(), beneficio2);

        assertThat(resposta).isEqualTo(new ResponseEntity<>(HttpStatus.ACCEPTED));
        assertThat(beneficioRepository.findFirstByNome("Auxilio2")).isNotEqualTo(beneficio);
    }

}
