package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.validations.Validator;
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

    private final List<Validator<ArtistRequest>> validators;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, List<Validator<ArtistRequest>> validators) {
        this.artistRepository = artistRepository;
        this.validators = validators;
    }

    @Override
    public ArtistResponse create(ArtistRequest artistRequest) {
		log.info(Messages.CREATING_ARTIST);
        validators.forEach(v -> v.validate(artistRequest));
        try {
            Artist artist = ArtistMapper.toEntity(artistRequest);
            this.artistRepository.save(artist);

            ArtistResponse artistResponse = ArtistMapper.toResponse(artist);

            return artistResponse;
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ArtistResponse update(Long id, ArtistRequest artistRequest) {
		log.info(Messages.UPDATING_ARTIST);
        Artist artist = ArtistMapper.toEntity(artistRequest);
        this.findById(id);
        try {
            artist.setId(id);
            this.artistRepository.save(artist);
            ArtistResponse artistResponse = ArtistMapper.toResponse(artist);
			log.info(Messages.ARTIST_SUCCESSFULLY_UPDATED);
            return artistResponse;
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ArtistResponse findById(Long id) {
		log.info(Messages.SEARCHING_ARTIST_BY_ID, id);
        try {
            Optional<Artist> artist = this.artistRepository.findById(id);
            if (artist.isEmpty()) {
                throw new ArtistException("Artist not found.", HttpStatus.NOT_FOUND);
            }
            ArtistResponse artistResponse = ArtistMapper.toResponse(artist.get());
            return artistResponse;
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArtistResponse> listAll() {
        log.info(Messages.LISTING_ALL_ARTISTS);
        try {
            List<Artist> artists = this.artistRepository.findAllByOrderByNameAsc();
            return ArtistMapper.toResponseList(artists);
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArtistResponse> listAllWithItems() {
        log.info(Messages.LISTING_ALL_ITEMS);
        try {
            List<Artist> artists = this.artistRepository.findAllByOrderByNameAsc();
            return ArtistMapper.toResponseList(artists);
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ArtistItemDetailsResponse> listArtistsItemsDetails() {
        log.info(Messages.LISTING_ALL_ITEMS_WITH_DETAILS);
        List<ArtistItemDetailsResponse> artists = this.artistRepository.getArtistsItemsDetails().stream().map(a -> {
            ArtistItemDetailsResponse artistDTO = new ArtistItemDetailsResponse(a.id(), a.name(), a.country(), a.title(), a.genre(), a.category(), a.releaseYear());
            return artistDTO;
        }).collect(Collectors.toList());

        return artists;
    }

    @Override
    public void delete(Long id) {
        log.info(Messages.DELETING_ARTIST_BY_ID, id);
        try {
            this.findById(id);
            this.artistRepository.deleteById(id);
            log.info(Messages.ARTIST_SUCCESSFULLY_DELETED);
        } catch (ArtistException ce) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}