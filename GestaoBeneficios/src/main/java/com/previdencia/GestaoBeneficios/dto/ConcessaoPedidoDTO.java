package com.previdencia.GestaoBeneficios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcessaoPedidoDTO {
    private String requisitante;
    private String beneficiado;
    private String beneficioNome;
}
