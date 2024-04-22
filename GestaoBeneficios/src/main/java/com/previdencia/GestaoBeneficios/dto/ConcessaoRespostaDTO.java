package com.previdencia.GestaoBeneficios.dto;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcessaoRespostaDTO {
    private UUID id;
    private long requisitante;
    private long beneficiado;
    private LocalDate data;
    private double valor;
    private boolean status;
    private Beneficio beneficio;
}
