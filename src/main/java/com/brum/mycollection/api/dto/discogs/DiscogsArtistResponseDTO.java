package com.brum.mycollection.api.dto.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsArtistResponseDTO {

    private Integer id;

    private String name;

    @JsonProperty("realname")
    private String realName;

    private String profile;

    @JsonProperty("data_quality")
    private String dataQuality;

    @JsonProperty("namevariations")
    private List<String> nameVariations;

    private List<DiscogsImageDTO> images;

    @JsonProperty("resource_url")
    private String resourceUrl;

    private String uri;

    @JsonProperty("releases_url")
    private String releasesUrl;
}
