package org.acme.service;

import java.util.List;

import org.acme.dto.MunicipioDTO;
import org.acme.dto.MunicipioResponseDTO;

public interface MunicipioService {

    List<MunicipioResponseDTO> getAll();

    MunicipioResponseDTO findById(Long id);

    MunicipioResponseDTO create(MunicipioDTO productDTO);

    MunicipioResponseDTO update(Long id, MunicipioDTO productDTO);

    void delete(Long id);

    List<MunicipioResponseDTO> findByNome(String nome);

    long count();

}
