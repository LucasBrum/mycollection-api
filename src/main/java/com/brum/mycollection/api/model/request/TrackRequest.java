package com.brum.mycollection.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrackRequest(

        @JsonProperty("position")
        String position,

        @JsonProperty("title")
        String title,

        @JsonProperty("duration")
        String duration,

        @JsonProperty("trackOrder")
        Integer trackOrder

) {}
