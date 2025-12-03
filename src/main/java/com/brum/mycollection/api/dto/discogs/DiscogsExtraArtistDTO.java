package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsExtraArtistDTO {
  private Integer id;
  private String name;
  private String role;  // Ex: "Guitar", "Producer", "Mixed By"

}
