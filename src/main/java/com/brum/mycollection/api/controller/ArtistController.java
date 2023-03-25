package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.ArtistDTO;
import com.brum.mycollection.api.entity.CoverImage;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.repository.CoverImageRepository;
import com.brum.mycollection.api.service.ArtistService;
import com.brum.mycollection.api.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private CoverImageRepository coverImageRepository;

    @PostMapping
    public ResponseEntity<Response<ArtistDTO>> create(@RequestPart("artist") ArtistDTO artistDTO, @RequestPart("image") MultipartFile file) throws IOException {
        ArtistDTO artistCreated = this.artistService.create(artistDTO, file);
        Response<ArtistDTO> response = new Response<>();
        response.setData(artistCreated);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<ArtistDTO>>> list() {
        List<ArtistDTO> artistDTOList = this.artistService.list();

        Response<List<ArtistDTO>> response = new Response<>();
        response.setData(artistDTOList);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ArtistDTO>> findById(@PathVariable Long id) {
        ArtistDTO artistDTO = this.artistService.findById(id);

        Response<ArtistDTO> response = new Response<>();
        response.setData(artistDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/album/cover/{id}")
    public ResponseEntity<byte[]> getCoverFromAlbum(@PathVariable Long id) {
        Optional<CoverImage> coverImage = coverImageRepository.findById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(coverImage.get().getType()));

        return new ResponseEntity<>(ImageUtility.decompressImage(coverImage.get().getImage()), headers, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<ArtistDTO>> update(@PathVariable Long id, @RequestBody ArtistDTO artistDTO) {
        ArtistDTO artistUpdated = artistService.update(id, artistDTO);

        Response<ArtistDTO> response = new Response<>();
        response.setData(artistUpdated);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {
        this.artistService.delete(id);

        Response<Boolean> response = new Response<>();
        response.setData(Boolean.TRUE);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}