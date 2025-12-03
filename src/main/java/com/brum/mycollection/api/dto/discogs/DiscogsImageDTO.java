package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsImageDTO {
  private String type;    // "primary" ou "secondary"
  private String uri;     // URL da imagem em tamanho original
  private String uri150;  // URL da imagem em 150px
  private Integer width;
  private Integer height;
}
