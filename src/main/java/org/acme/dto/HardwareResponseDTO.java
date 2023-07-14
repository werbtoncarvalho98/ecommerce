package org.acme.dto;

import java.time.LocalDate;

import org.acme.model.Hardware;
import org.acme.model.Integridade;
import org.acme.model.Nivel;

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
        FabricanteResponseDTO fabricante,
        String imagem) {

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
                new FabricanteResponseDTO(hardware.getFabricante()),
                hardware.getImagem());
    }
}