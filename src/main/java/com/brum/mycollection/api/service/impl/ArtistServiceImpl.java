package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.ArtistDTO;
import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
	public ArtistDTO create(ArtistDTO artistDTO) {
		Boolean artistFounded = artistRepository.existsArtistByBandAndTitle(artistDTO.getBand(), artistDTO.getTitle());

		if (artistFounded) {
			throw new ArtistException("Album já cadastrado.", HttpStatus.BAD_REQUEST);
		}

		try {

            Artist artist = this.mapper.map(artistDTO, Artist.class);

			this.artistRepository.save(artist);
			artistDTO = mapper.map(artist, ArtistDTO.class);

			return artistDTO;
		} catch (Exception e) {
			throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ArtistDTO update(Long id, ArtistDTO artistDTO) {
		ArtistDTO artistDTOFound = this.findById(id);
		try {
			artistDTO.setId(id);
			Artist artist = mapper.map(artistDTO, Artist.class);
			this.artistRepository.save(artist);
			artistDTO = mapper.map(artist, ArtistDTO.class);
			return artistDTO;
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ArtistDTO findById(Long id) {
		try {
			Optional<Artist> artist = this.artistRepository.findById(id);
			if (artist.isPresent()) {
				ArtistDTO artistDTO = mapper.map(artist.get(), ArtistDTO.class);
				return artistDTO;
			}

			throw new ArtistException("Artista não encontrado.", HttpStatus.NOT_FOUND);
		} catch (ArtistException aex) {
			throw aex;
		} catch (Exception e) {
			throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<ArtistDTO> list() {
		try {
			List<Artist> artists = this.artistRepository.findAllByOrderByBandAsc();
			return this.mapper.map(artists, new TypeToken<List<ArtistDTO>>() {
			}.getType());
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