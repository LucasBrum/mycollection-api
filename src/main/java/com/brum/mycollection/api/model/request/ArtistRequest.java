package com.brum.mycollection.api.model.request;

import com.brum.mycollection.api.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtistRequest {

    @JsonProperty("name")
    @NotBlank(message = "Informe o Artista")
    private String name;

    @JsonProperty("country")
    @NotBlank(message = "Informe o País")
    private String country;

}