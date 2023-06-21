package com.brum.mycollection.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistResponse(

        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String name,

        @JsonProperty("country")
        String country

) {}