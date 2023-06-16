package com.brum.mycollection.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryResponse(@JsonProperty("id") Long id, @JsonProperty("name") String name) {

}