package com.previdencia.GestaoBeneficios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcessaoPedidoDTO {
    private long requisitante;
    private long beneficiado;
    private long beneficio;
}
