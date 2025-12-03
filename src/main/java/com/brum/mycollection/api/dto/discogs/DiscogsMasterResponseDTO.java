package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsMasterResponseDTO {
  private Integer id;

  private String title;

  private Integer year;  // Ano original do lançamento

  private List<DiscogsArtitsDTO> artists;

  private List<String> genres;

  private List<String> styles;

  private List<DiscogsTrackDTO> tracklist;

  private List<DiscogsImageDTO> images;
}
