package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsFormatDTO {
  private String name;
  private String qty;
  private String text;
  private List<String> descriptions;
}
