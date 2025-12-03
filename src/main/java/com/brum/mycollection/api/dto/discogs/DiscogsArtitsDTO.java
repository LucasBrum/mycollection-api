package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsArtitsDTO {
  private Integer id;
  private String name;

  @JsonProperty("resource_url")
  private String resourceUrl;
}
