package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsSearchResultDTO {
  private Integer id;

  @JsonProperty("master_id")
  private Integer masterId;

  private String title;

  private String year;

  @JsonProperty("cover_image")
  private String coverImage;

  private String thumb;

  private List<String> genre;

  private List<String> style;

  private List<String> label;

  private String country;

  private List<String> format;

  @JsonProperty("resource_url")
  private String resourceUrl;

}
