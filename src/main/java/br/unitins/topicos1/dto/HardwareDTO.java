package br.unitins.topicos1.dto;

import java.util.Date;

import br.unitins.topicos1.model.Fabricante;

public record HardwareDTO(

        String modelo,
        Date lancamento,
        Integer nivel,
        Integer integridade,
        Fabricante fabricante) {
}