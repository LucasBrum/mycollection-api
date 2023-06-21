package com.brum.mycollection.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryRequest(
        @JsonProperty("name")
        String name)
{}