package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.dto.ArtistDTO;

import java.util.List;

public interface CustomArtistRepository {

    List<ArtistDTO> findArtistsAndItems();
}
