package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsSearchResponseDTO {
  private Pagination pagination;

  private List<DiscogsSearchResultDTO> results;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Pagination {
    private int page;
    private int pages;

    @JsonProperty("per_page")
    private int perPage;

    private int items;
  }
}
