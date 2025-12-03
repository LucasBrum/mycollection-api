package com.brum.mycollection.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditRequest(

        @JsonProperty("name")
        String name,

        @JsonProperty("role")
        String role,

        @JsonProperty("discogsArtistId")
        Integer discogsArtistId

) {}
