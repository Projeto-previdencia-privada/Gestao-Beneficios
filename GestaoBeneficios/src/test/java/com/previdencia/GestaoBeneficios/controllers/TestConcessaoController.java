package com.previdencia.GestaoBeneficios.controllers;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import com.previdencia.GestaoBeneficios.repository.ConcessaoRepository;
import com.previdencia.GestaoBeneficios.services.BeneficioService;
import com.previdencia.GestaoBeneficios.services.ConcessaoService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
public class TestConcessaoController {

    @Autowired
    private BeneficioRepository beneficioRepository;
    @Autowired
    private BeneficioService beneficioService;
    @Autowired
    private BeneficioController beneficioController;
    @Autowired
    private ConcessaoController concessaoController;
    @Autowired
    private ConcessaoService concessaoService;
    @Autowired
    private ConcessaoRepository concessaoRepository;

    @AfterEach
    void afterEach(){
        concessaoRepository.deleteAllInBatch();
    }

    @Test
    public void testPostConcessao() {

        String nome= "Auxilio Emergencial";
        Long cpf= 12345678910L;

        Beneficio beneficio = beneficio = new Beneficio(
                nome,
                50,
                1,
                true
        );
        beneficioController.BeneficioPost(beneficio);
        concessaoController.ConcessaoPost(cpf,
                beneficioRepository.findFirstByNome(nome).getId());

        assertThat(concessaoRepository.findFirstByRequisitante(cpf).getRequisitante())
                .isEqualTo(cpf);
        assertThat(concessaoRepository.findFirstByRequisitante(cpf).getBeneficiado())
                .isEqualTo(cpf);


    }
    @Test
    public void testSomaConcessao() {
        String nome= "Auxilio Casa";
        Long cpf= 12345678910L;
        Beneficio beneficio = beneficio = new Beneficio(
                nome,
                50,
                1,
                true
        );
        beneficioController.BeneficioPost(beneficio);
        concessaoController.ConcessaoPost(cpf,
                beneficioRepository.findFirstByNome(nome).getId());
        concessaoController.ConcessaoPost(cpf,
                beneficioRepository.findFirstByNome(nome).getId());
        concessaoController.ConcessaoPost(cpf,
                beneficioRepository.findFirstByNome(nome).getId());

        Double soma =concessaoController.GetSoma
                (concessaoRepository.findFirstByRequisitante(cpf).getRequisitante());

        assertThat(soma).isEqualTo(3*concessaoRepository.
                findFirstByRequisitante(cpf).getValor());

    }

    @Test
    public void testPostConcessaoNaoIndividual() {

        String nome= "Auxilio";
        Long cpf= 12345678910L;
        Long beneficiado = 11345178911L;

        Beneficio beneficio = beneficio = new Beneficio(
                nome,
                50,
                1,
                false
        );
        beneficioController.BeneficioPost(beneficio);
        concessaoController.ConcessaoPost(cpf,
                beneficioRepository.findFirstByNome(nome).getId(),beneficiado);

        assertThat(concessaoRepository.findFirstByRequisitante(cpf).getRequisitante())
                .isEqualTo(cpf);
        assertThat(concessaoRepository.findFirstByRequisitante(cpf).getBeneficiado())
                .isEqualTo(beneficiado);


    }
}