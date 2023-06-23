package com.brum.mycollection.api.validations.artist;

import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.validations.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateIfArtistExistsByName implements Validator<ArtistRequest> {

    private final ArtistRepository artistRepository;

    public ValidateIfArtistExistsByName(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void validate(ArtistRequest artistRequest) {
        log.info(Messages.CHECKING_IF_THE_ARTIST_ALREADY_EXISTS, artistRequest.name());
        Boolean isArtistFounded = artistRepository.existsArtistByName(artistRequest.name());

        if (isArtistFounded) {
            log.info(Messages.ARTIST_ALREADY_EXISTS, artistRequest.name());
            throw new ArtistException(Messages.ARTIST_ALREADY_EXISTS_EXCEPTION, HttpStatus.BAD_REQUEST);
        }
    }
}