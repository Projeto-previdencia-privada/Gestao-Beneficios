package com.previdencia.GestaoBeneficios.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import com.previdencia.GestaoBeneficios.services.BeneficioService;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
public class TestBeneficioService {
    private Beneficio beneficio;

    @BeforeEach
    public void initEach(){
        beneficio = new Beneficio("Auxilio", 10, 3, true);
    }

    @AfterEach
    public void cleanUpEach(){
        beneficioRepository.delete(beneficio);
        beneficio = null;
    }

    @Autowired
    private BeneficioService beneficioService;

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Test
    void testBeneficioPost() {

        ResponseEntity<Object> beneficioSalvo =  beneficioService.adicionar(beneficio);
        ResponseEntity<Object> responseOk = new ResponseEntity<>(HttpStatus.CREATED);

        assertThat(beneficioSalvo).isEqualTo(responseOk);
    }

    @Test
    void testBeneficioDelete() {

        beneficioService.adicionar(beneficio);

        ResponseEntity<Object> responseOk = new ResponseEntity<>(HttpStatus.ACCEPTED);
        ResponseEntity<Object> beneficioApagado =  beneficioService.remover(beneficioRepository
                .findFirstByNome("Auxilio").getId());

        assertThat(beneficioApagado).isEqualTo(responseOk);
    }

    @Test
    void testBeneficioPut() {
        Beneficio newBeneficio = new Beneficio("Auxilio 2", 12, 31, false);

        beneficioService.adicionar(beneficio);

        Beneficio oldBeneficio = beneficioRepository
                .findFirstByNome("Auxilio");
        long idBeneficio= beneficioRepository
                .findFirstByNome("Auxilio").getId();

        ResponseEntity<Object> responseOk = new ResponseEntity<>(HttpStatus.ACCEPTED);
        ResponseEntity<Object> beneficioAlterado =  beneficioService.alterar(idBeneficio, newBeneficio);

        assertThat(beneficioAlterado).isEqualTo(responseOk);
        assertThat(beneficioRepository.findById(idBeneficio)).isNotEqualTo(oldBeneficio);
    }

}
