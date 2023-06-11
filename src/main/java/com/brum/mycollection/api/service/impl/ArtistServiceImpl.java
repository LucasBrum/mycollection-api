package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.ArtistDTO;
import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.model.response.ArtistResponse;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.service.ArtistService;
import com.brum.mycollection.api.util.ImageUtility;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

	private final ArtistRepository artistRepository;

	private final ModelMapper mapper;

	@Autowired
	public ArtistServiceImpl(ArtistRepository artistRepository) {
		this.mapper = new ModelMapper();
		this.artistRepository = artistRepository;
	}

	@Override
	public ArtistResponse create(ArtistRequest artistRequest, MultipartFile file) throws IOException {
		Boolean isArtistFounded = artistRepository.existsArtistByBandAndTitle(artistRequest.getBand(), artistRequest.getTitle());

		if (isArtistFounded) {
			throw new ArtistException("Album já cadastrado.", HttpStatus.BAD_REQUEST);
		}

		try {
            Artist artist = this.mapper.map(artistRequest, Artist.class);
			artist.setCoverImage(ImageUtility.compressImage(file.getBytes()));
			this.artistRepository.save(artist);
			var artistResponse = mapper.map(artist, ArtistResponse.class);

			return artistResponse;
		} catch (Exception e) {
			throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ArtistResponse update(Long id, ArtistRequest artistRequest) {
		Artist artist = this.mapper.map(artistRequest, Artist.class);
		this.findById(id);
		try {
			artist.setId(id);
			this.artistRepository.save(artist);
			var artistResponse = mapper.map(artist, ArtistResponse.class);
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
				ArtistResponse artistResponse = mapper.map(artist.get(), ArtistResponse.class);
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
	public byte[] findCoverImageById(Long id) {
		try {
			Optional<Artist> artist = this.artistRepository.findById(id);
			if (artist.isPresent()) {
				byte[] coverImage = artist.get().getCoverImage();
				return coverImage;
			}

			throw new ArtistException("Artista não encontrado.", HttpStatus.NOT_FOUND);
		} catch (ArtistException aex) {
			throw aex;
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<ArtistResponse> list() {
		try {
			List<Artist> artists = this.artistRepository.findAllByOrderByBandAsc();
			return this.mapper.map(artists, new TypeToken<List<ArtistResponse>>() {}.getType());
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
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