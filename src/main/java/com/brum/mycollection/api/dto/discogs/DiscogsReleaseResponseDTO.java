package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsReleaseResponseDTO {
  private Integer id;

  @JsonProperty("master_id")
  private Integer masterId;

  private String title;

  private Integer year;

  private List<DiscogsArtitsDTO> artists;

  private List<DiscogsLabelDTO> labels;

  private List<String> genres;

  private List<String> styles;

  private List<DiscogsTrackDTO> tracklist;

  private List<DiscogsImageDTO> images;

  @JsonProperty("extraartists")
  private List<DiscogsExtraArtistDTO> extraArtists;

  private String country;

  private List<DiscogsFormatDTO> formats;

}
