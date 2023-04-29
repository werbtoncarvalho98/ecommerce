package br.unitins.topicos1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.topicos1.model.Hardware;
import br.unitins.topicos1.model.Integridade;
import br.unitins.topicos1.model.Nivel;

public record HardwareResponseDTO(

        Long id,
        String modelo,
        Date lancamento,
        @JsonInclude(JsonInclude.Include.NON_NULL) Nivel nivel,
        Integridade integridade,
        FabricanteResponseDTO fabricante) {

    public HardwareResponseDTO(Hardware hardware) {
        this(hardware.getId(), hardware.getModelo(), hardware.getLancamento(), hardware.getNivel(),
                hardware.getIntegridade(), new FabricanteResponseDTO(hardware.getFabricante()));
    }
}