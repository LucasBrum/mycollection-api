package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.ArtistDTO;

import java.util.List;

public interface ArtistService {

    ArtistDTO create(ArtistDTO categoryDTO);

    ArtistDTO update(Long id, ArtistDTO categoryDTO);

    ArtistDTO findById(Long id);

    List<ArtistDTO> list();

    void delete(Long id);

}
