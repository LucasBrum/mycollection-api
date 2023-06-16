package com.brum.mycollection.api.service;

import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.model.response.ArtistItemDetailsResponse;
import com.brum.mycollection.api.model.response.ArtistResponse;

import java.util.List;

public interface ArtistService {

    ArtistResponse create(ArtistRequest artistRequest);
    ArtistResponse update(Long id, ArtistRequest artistRequest);
    ArtistResponse findById(Long id);

    List<ArtistResponse> listAll();
    List<ArtistResponse> listAllWithItems();
    List<ArtistItemDetailsResponse> listAllArtistsAndItems();

    void delete(Long id);

}
