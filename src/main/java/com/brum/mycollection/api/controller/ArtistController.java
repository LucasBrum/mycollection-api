package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.model.response.ArtistItemDetailsResponse;
import com.brum.mycollection.api.model.response.ArtistResponse;
import com.brum.mycollection.api.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;


    @PostMapping
    public ResponseEntity<Response<ArtistResponse>> create(@RequestBody ArtistRequest artistRequest) {
        ArtistResponse artistCreatedResponse = this.artistService.create(artistRequest);
        Response<ArtistResponse> response = new Response<>();
        response.setData(artistCreatedResponse);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<ArtistResponse>>> listAll() {
        List<ArtistResponse> artistListResponse = this.artistService.listAll();

        Response<List<ArtistResponse>> response = new Response<>();
        response.setData(artistListResponse);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/items/details")
    public ResponseEntity<Response<List<ArtistItemDetailsResponse>>> listAllArtistsAndItems() {
        List<ArtistItemDetailsResponse> artistListResponse = this.artistService.listAllArtistsAndItems();

        Response<List<ArtistItemDetailsResponse>> response = new Response<>();
        response.setData(artistListResponse);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ArtistResponse>> findById(@PathVariable Long id) {
        ArtistResponse artistResponse = this.artistService.findById(id);

        Response<ArtistResponse> response = new Response<>();
        response.setData(artistResponse);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<ArtistResponse>> update(@PathVariable Long id, @RequestBody ArtistRequest artistRequest) {
        ArtistResponse artistResponse = artistService.update(id, artistRequest);

        Response<ArtistResponse> response = new Response<>();
        response.setData(artistResponse);
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