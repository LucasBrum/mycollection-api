package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Artist;
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

@Service
@Slf4j
public class ArtistServiceImpl implements ArtistService {

	private final ArtistRepository artistRepository;

	@Autowired
	public ArtistServiceImpl(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	@Override
	public ArtistResponse create(ArtistRequest artistRequest) {
		Boolean isArtistFounded = artistRepository.existsArtistByName(artistRequest.name());

		if (isArtistFounded) {
			throw new ArtistException("Album já cadastrado.", HttpStatus.BAD_REQUEST);
		}

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
		Artist artist = ArtistMapper.toEntity(artistRequest);
		this.findById(id);
		try {
			artist.setId(id);
			this.artistRepository.save(artist);
			ArtistResponse artistResponse = ArtistMapper.toResponse(artist);
			return artistResponse;
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ArtistResponse findById(Long id) {
		try {
			Optional<Artist> artist = this.artistRepository.findById(id);
			if (artist.isPresent()) {
				ArtistResponse artistResponse = ArtistMapper.toResponse(artist.get());
				return artistResponse;
			}

			throw new ArtistException("Artista não encontrado.", HttpStatus.NOT_FOUND);
		} catch (ArtistException aex) {
			throw aex;
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	@Override
	public List<ArtistResponse> listAll() {
		try {
			List<Artist> artists = this.artistRepository.findAllByOrderByNameAsc();
			return ArtistMapper.toResponseList(artists);
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<ArtistResponse> listAllWithItems() {
		try {
			List<Artist> artists = this.artistRepository.findAllByOrderByNameAsc();
			return ArtistMapper.toResponseList(artists);
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<ArtistItemDetailsResponse> listAllArtistsAndItems() {
		List<ArtistItemDetailsResponse> artists = this.artistRepository.getArtistsAndItems().stream().map(a -> {
			ArtistItemDetailsResponse artistDTO = new ArtistItemDetailsResponse(a.id(), a.name(), a.country(), a.title(), a.genre(), a.category(), a.releaseYear());

			return artistDTO;
		}).collect(Collectors.toList());

		return artists;
	}

	@Override
	public void delete(Long id) {
		try {
			this.findById(id);

			this.artistRepository.deleteById(id);
		} catch (ArtistException ce) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}