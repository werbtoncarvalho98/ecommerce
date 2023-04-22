package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;

public interface ClienteService {
    
    List<ClienteResponseDTO> getAll();

    ClienteResponseDTO findById(Long id);

    ClienteResponseDTO create(ClienteDTO clienteDTO);

    ClienteResponseDTO update(Long id, ClienteDTO clienteDTO);

    void delete(Long id);

    List<ClienteResponseDTO> findByCpf(String cpf);

    Long count();
}