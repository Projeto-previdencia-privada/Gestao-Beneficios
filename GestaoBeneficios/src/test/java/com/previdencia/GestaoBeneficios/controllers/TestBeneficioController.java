package com.previdencia.GestaoBeneficios.controllers;

import com.previdencia.GestaoBeneficios.services.BeneficioService;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBeneficioController {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:alpine3.18"
    );
    private BeneficioController beneficioController;
    private BeneficioService beneficioService;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnectionProvider connectionProvider = new DBConnectionProvider(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        customerService = new CustomerService(connectionProvider);
    }
}
