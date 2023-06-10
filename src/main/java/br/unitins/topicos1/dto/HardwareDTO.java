package br.unitins.topicos1.dto;

import java.time.LocalDate;

public record HardwareDTO(
        String modelo,
        LocalDate lancamento,
        Integer idNivel,
        Integer idIntegridade,
        Long fabricante) {
}