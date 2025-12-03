package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.config.DiscogsConfig;
import com.brum.mycollection.api.dto.discogs.DiscogsArtistResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsMasterResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsReleaseResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsSearchResponseDTO;
import com.brum.mycollection.api.service.DiscogsService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class DiscogsServiceImpl implements DiscogsService {
  private final RestTemplate restTemplate;
  private final DiscogsConfig discogsConfig;

  public DiscogsServiceImpl(RestTemplate restTemplate, DiscogsConfig discogsConfig) {
    this.restTemplate = restTemplate;
    this.discogsConfig = discogsConfig;
  }

  @Override
  public DiscogsSearchResponseDTO search(String query, String type, int page, int perPage) {
    // Constrói a URL com os parâmetros de busca
    String url = UriComponentsBuilder.fromHttpUrl(discogsConfig.getBaseUrl())
            .path("/database/search")
            .queryParam("q", query)
            .queryParam("type", type)
            .queryParam("page", page)
            .queryParam("per_page", perPage)
            .build()
            .toUriString();

    // Cria os headers com autenticação
    HttpHeaders headers = createHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    // Faz a requisição GET
    ResponseEntity<DiscogsSearchResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiscogsSearchResponseDTO.class
    );

    return response.getBody();
  }

  @Override
  public DiscogsSearchResponseDTO searchByArtist(String artistName, int page, int perPage) {
    // Usa o parâmetro "artist" para busca específica por artista
    String url = UriComponentsBuilder.fromHttpUrl(discogsConfig.getBaseUrl())
            .path("/database/search")
            .queryParam("artist", artistName)
            .queryParam("type", "release")
            .queryParam("format", "CD")  // Filtra apenas CDs
            .queryParam("page", page)
            .queryParam("per_page", perPage)
            .build()
            .toUriString();

    HttpHeaders headers = createHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<DiscogsSearchResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiscogsSearchResponseDTO.class
    );

    return response.getBody();
  }

  @Override
  public DiscogsSearchResponseDTO searchByAlbum(String albumTitle, int page, int perPage) {
    // Usa o parâmetro "release_title" para busca específica por álbum
    String url = UriComponentsBuilder.fromHttpUrl(discogsConfig.getBaseUrl())
            .path("/database/search")
            .queryParam("release_title", albumTitle)
            .queryParam("type", "release")
            .queryParam("format", "CD")  // Filtra apenas CDs
            .queryParam("page", page)
            .queryParam("per_page", perPage)
            .build()
            .toUriString();

    HttpHeaders headers = createHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<DiscogsSearchResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiscogsSearchResponseDTO.class
    );

    return response.getBody();
  }

  @Override
  public DiscogsReleaseResponseDTO getReleaseDetails(Integer releaseId) {
    // Endpoint para obter detalhes de um release específico
    String url = discogsConfig.getBaseUrl() + "/releases/" + releaseId;

    HttpHeaders headers = createHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<DiscogsReleaseResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiscogsReleaseResponseDTO.class
    );

    return response.getBody();
  }

  @Override
  public DiscogsArtistResponseDTO getArtistDetails(Integer artistId) {
    // Endpoint para obter detalhes de um artista específico
    String url = discogsConfig.getBaseUrl() + "/artists/" + artistId;

    HttpHeaders headers = createHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<DiscogsArtistResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiscogsArtistResponseDTO.class
    );

    return response.getBody();
  }

  @Override
  public DiscogsMasterResponseDTO getMasterDetails(Integer masterId) {
    // Endpoint para obter detalhes de um master release (álbum original)
    String url = discogsConfig.getBaseUrl() + "/masters/" + masterId;

    HttpHeaders headers = createHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<DiscogsMasterResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiscogsMasterResponseDTO.class
    );

    return response.getBody();
  }

  /**
   * Cria os headers necessários para autenticação na API do Discogs
   */
  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    // Token de autenticação
    headers.set("Authorization", "Discogs token=" + discogsConfig.getToken());
    // User-Agent é obrigatório pela API do Discogs
    headers.set("User-Agent", discogsConfig.getUserAgent());
    // Aceita JSON como resposta
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    return headers;
  }

}
