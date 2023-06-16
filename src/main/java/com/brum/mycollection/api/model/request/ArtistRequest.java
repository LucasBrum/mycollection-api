package com.brum.mycollection.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record ArtistRequest(
        @JsonProperty("name")
        @NotBlank(message = "Informe o Artista")
        String name,

        @JsonProperty("country")
        @NotBlank(message = "Informe o País")
        String country
){}