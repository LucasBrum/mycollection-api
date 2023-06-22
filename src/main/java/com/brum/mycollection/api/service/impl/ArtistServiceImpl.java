package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.domain.artist.Artist;
import com.brum.mycollection.api.domain.artist.validations.ValidatorArtist;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.mapper.ArtistMapper;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.model.response.ArtistItemDetailsResponse;
import com.brum.mycollection.api.model.response.ArtistResponse;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.service.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final List<ValidatorArtist> validators;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, List<ValidatorArtist> validators) {
        this.artistRepository = artistRepository;
        this.validators = validators;
    }

    @Override
    public ArtistResponse create(ArtistRequest artistRequest) {
		log.info("Creating Artist");
        validators.forEach(v -> v.validate(artistRequest));
        try {
            Artist artist = ArtistMapper.toEntity(artistRequest);
            this.artistRepository.save(artist);

            ArtistResponse artistResponse = ArtistMapper.toResponse(artist);

            return artistResponse;
        } catch (Exception e) {
            throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ArtistResponse update(Long id, ArtistRequest artistRequest) {
		log.info("Updating Artist");
        Artist artist = ArtistMapper.toEntity(artistRequest);
        this.findById(id);
        try {
            artist.setId(id);
            this.artistRepository.save(artist);
            ArtistResponse artistResponse = ArtistMapper.toResponse(artist);
			log.info("Artist updated with success.");
            return artistResponse;
        } catch (Exception e) {
            throw new ArtistException("Internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ArtistResponse findById(Long id) {
		log.info("Searching Artist by Id {}.", id);
        try {
            Optional<Artist> artist = this.artistRepository.findById(id);
            if (artist.isEmpty()) {
                throw new ArtistException("Artist not found.", HttpStatus.NOT_FOUND);
            }
            ArtistResponse artistResponse = ArtistMapper.toResponse(artist.get());
            return artistResponse;
        } catch (Exception e) {
            throw new ArtistException("Internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArtistResponse> listAll() {
        log.info("Listing All Artists.");
        try {
            List<Artist> artists = this.artistRepository.findAllByOrderByNameAsc();
            return ArtistMapper.toResponseList(artists);
        } catch (Exception e) {
            throw new ArtistException("Internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArtistResponse> listAllWithItems() {
        log.info("Listing all Items.");
        try {
            List<Artist> artists = this.artistRepository.findAllByOrderByNameAsc();
            return ArtistMapper.toResponseList(artists);
        } catch (Exception e) {
            throw new ArtistException("Internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArtistItemDetailsResponse> listArtistsItemsDetails() {
        log.info("Listing all items with details.");
        List<ArtistItemDetailsResponse> artists = this.artistRepository.getArtistsItemsDetails().stream().map(a -> {
            ArtistItemDetailsResponse artistDTO = new ArtistItemDetailsResponse(a.id(), a.name(), a.country(), a.title(), a.genre(), a.category(), a.releaseYear());
            return artistDTO;
        }).collect(Collectors.toList());

        return artists;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Artist.");
        try {
            this.findById(id);
            this.artistRepository.deleteById(id);
            log.info("Artist successfully deleted.");
        } catch (ArtistException ce) {
            throw new ArtistException("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}