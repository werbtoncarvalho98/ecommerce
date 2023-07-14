package org.acme.service;

import java.util.List;

import org.acme.dto.EnderecoDTO;
import org.acme.dto.EnderecoResponseDTO;

public interface EnderecoService {
    
    List<EnderecoResponseDTO> getAll();

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(EnderecoDTO enderecoDTO);

    EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO);

    void delete(Long id);

    List<EnderecoResponseDTO> findByCep(String cep);

    Long count();
}