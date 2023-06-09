package br.unitins.topicos1.dto;

import java.util.Date;

public record HardwareDTO(
                String modelo,
                Date lancamento,
                Integer nivel,
                Integer integridade,
                Long idFabricante) {
}