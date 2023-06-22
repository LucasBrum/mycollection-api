package com.brum.mycollection.api.domain.artist.validations;

import com.brum.mycollection.api.domain.artist.Artist;
import com.brum.mycollection.api.model.request.ArtistRequest;

import java.util.Optional;

public interface ArtistValidator {

    void validate(ArtistRequest artistRequest);

}
