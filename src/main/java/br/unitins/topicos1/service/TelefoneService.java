package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.TelefoneResponseDTO;

public interface TelefoneService {
    
    List<TelefoneResponseDTO> getAll();

    TelefoneResponseDTO findById(Long id);

    TelefoneResponseDTO create(TelefoneDTO telefoneDTO);

    TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO);

    void delete(Long id);

    List<TelefoneResponseDTO> findByNumero(String numero);

    Long count();
}