package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.ArtistDTO;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.model.response.ArtistResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArtistService {

    ArtistResponse create(ArtistRequest artistRequest, MultipartFile file) throws IOException;

    ArtistResponse update(Long id, ArtistRequest artistRequest);

    ArtistResponse findById(Long id);

    List<ArtistResponse> list();

    void delete(Long id);

    byte[] findCoverImageById(Long id);

}
