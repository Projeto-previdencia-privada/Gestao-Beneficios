package com.previdencia.GestaoBeneficios.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.previdencia.GestaoBeneficios.models.Beneficios;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class BeneficiosRepositoryTest {
	
	@Autowired
    private BeneficioRepository beneficioRepository;

	
	@Test
	public void testCreateBeneficio() {
		
		Beneficios beneficio = new Beneficios("Auxilio", 10, 3, true);
		
		Beneficios beneficioSalvo = beneficioRepository.save(beneficio);
		
		assertThat(beneficioSalvo).isNotNull();
		assertThat(beneficioSalvo.getId()).isGreaterThan(0);
	}

}
