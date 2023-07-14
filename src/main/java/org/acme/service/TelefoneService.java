package org.acme.service;

import java.util.List;

import org.acme.dto.TelefoneDTO;
import org.acme.dto.TelefoneResponseDTO;

public interface TelefoneService {
    
    List<TelefoneResponseDTO> getAll();

    TelefoneResponseDTO findById(Long id);

    TelefoneResponseDTO create(TelefoneDTO telefoneDTO);

    TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO);

    void delete(Long id);

    List<TelefoneResponseDTO> findByNumero(String numero);

    Long count();
}