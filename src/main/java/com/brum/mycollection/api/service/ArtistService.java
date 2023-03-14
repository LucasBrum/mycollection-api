package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.ArtistDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtistService {

    ArtistDTO create(ArtistDTO categoryDTO, MultipartFile file);

    ArtistDTO update(Long id, ArtistDTO categoryDTO);

    ArtistDTO findById(Long id);

    List<ArtistDTO> list();

    void delete(Long id);

}
