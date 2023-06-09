package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Pix;

public record PixResponseDTO(
        Long id,
        String chave) {
    public PixResponseDTO(Pix pix) {
        this(pix.getId(), pix.getChave());
    }
}