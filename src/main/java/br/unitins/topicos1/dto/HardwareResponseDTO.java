package br.unitins.topicos1.dto;

import java.time.LocalDate;

import br.unitins.topicos1.model.Hardware;
import br.unitins.topicos1.model.Integridade;
import br.unitins.topicos1.model.Nivel;

public record HardwareResponseDTO(

        Long id,
        String nome,
        String descricao,
        Float preco,
        Integer estoque,
        String modelo,
        LocalDate lancamento,
        Nivel nivel,
        Integridade integridade,
        FabricanteResponseDTO fabricante) {

    public HardwareResponseDTO(Hardware hardware) {
        this(
                hardware.getId(),
                hardware.getNome(),
                hardware.getDescricao(),
                hardware.getPreco(),
                hardware.getEstoque(),
                hardware.getModelo(),
                hardware.getLancamento(),
                hardware.getNivel(),
                hardware.getIntegridade(),
                new FabricanteResponseDTO(hardware.getFabricante()));
    }
}