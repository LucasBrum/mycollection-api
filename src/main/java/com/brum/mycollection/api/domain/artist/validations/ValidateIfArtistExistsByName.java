package com.brum.mycollection.api.domain.artist.validations;

import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.repository.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateIfArtistExistsByName implements ArtistValidator {

    private final ArtistRepository artistRepository;

    @Autowired
    public ValidateIfArtistExistsByName(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void validate(ArtistRequest artistRequest) {
        log.info("Checking if the Artist {} already exists.", artistRequest.name());
        Boolean isArtistFounded = artistRepository.existsArtistByName(artistRequest.name());

        if (isArtistFounded) {
            log.warn("Checking if the Artist {} already exists.", artistRequest.name());
            throw new ArtistException("Artist already registered;", HttpStatus.BAD_REQUEST);
        }
    }
}