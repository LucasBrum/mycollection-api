package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.discogs.DiscogsArtistResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsMasterResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsReleaseResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsSearchResponseDTO;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.service.DiscogsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discogs")
public class DiscogsController {
  private final DiscogsService discogsService;

  public DiscogsController(DiscogsService discogsService) {
    this.discogsService = discogsService;
  }

  /**
   * Busca geral no Discogs
   * GET /mycollection/api/discogs/search?query=Metallica&type=release&page=1&perPage=20
   */
  @GetMapping("/search")
  public ResponseEntity<Response<DiscogsSearchResponseDTO>> search(
          @RequestParam String query,
          @RequestParam(defaultValue = "release") String type,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "20") int perPage) {

    DiscogsSearchResponseDTO searchResponse = discogsService.search(query, type, page, perPage);

    Response<DiscogsSearchResponseDTO> response = new Response<>();
    response.setData(searchResponse);
    response.setStatusCode(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }

  /**
   * Busca releases por nome do artista
   * GET /mycollection/api/discogs/search/artist?name=Metallica&page=1&perPage=20
   */
  @GetMapping("/search/artist")
  public ResponseEntity<Response<DiscogsSearchResponseDTO>> searchByArtist(
          @RequestParam String name,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "20") int perPage) {

    DiscogsSearchResponseDTO searchResponse = discogsService.searchByArtist(name, page, perPage);

    Response<DiscogsSearchResponseDTO> response = new Response<>();
    response.setData(searchResponse);
    response.setStatusCode(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }

  /**
   * Busca releases por título do álbum
   * GET /mycollection/api/discogs/search/album?title=Master of Puppets&page=1&perPage=20
   */
  @GetMapping("/search/album")
  public ResponseEntity<Response<DiscogsSearchResponseDTO>> searchByAlbum(
          @RequestParam String title,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "20") int perPage) {

    DiscogsSearchResponseDTO searchResponse = discogsService.searchByAlbum(title, page, perPage);

    Response<DiscogsSearchResponseDTO> response = new Response<>();
    response.setData(searchResponse);
    response.setStatusCode(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }

  /**
   * Obtém detalhes completos de um release
   * GET /mycollection/api/discogs/releases/123456
   */
  @GetMapping("/releases/{releaseId}")
  public ResponseEntity<Response<DiscogsReleaseResponseDTO>> getReleaseDetails(
          @PathVariable Integer releaseId) {

    DiscogsReleaseResponseDTO releaseResponse = discogsService.getReleaseDetails(releaseId);

    Response<DiscogsReleaseResponseDTO> response = new Response<>();
    response.setData(releaseResponse);
    response.setStatusCode(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }

  /**
   * Obtém detalhes de um artista
   * GET /mycollection/api/discogs/artists/123456
   */
  @GetMapping("/artists/{artistId}")
  public ResponseEntity<Response<DiscogsArtistResponseDTO>> getArtistDetails(
          @PathVariable Integer artistId) {

    DiscogsArtistResponseDTO artistResponse = discogsService.getArtistDetails(artistId);

    Response<DiscogsArtistResponseDTO> response = new Response<>();
    response.setData(artistResponse);
    response.setStatusCode(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }

  /**
   * Obtém detalhes de um master release (álbum original)
   * GET /mycollection/api/discogs/masters/123456
   */
  @GetMapping("/masters/{masterId}")
  public ResponseEntity<Response<DiscogsMasterResponseDTO>> getMasterDetails(
          @PathVariable Integer masterId) {

    DiscogsMasterResponseDTO masterResponse = discogsService.getMasterDetails(masterId);

    Response<DiscogsMasterResponseDTO> response = new Response<>();
    response.setData(masterResponse);
    response.setStatusCode(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }
}
