package br.unitins.topicos1.dto;

import java.time.LocalDate;

public record CompraDTO(

                Long id,
                LocalDate date,
                Double totalcompra) {
}