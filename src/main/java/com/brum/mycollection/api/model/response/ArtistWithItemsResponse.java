package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArtistWithItemsResponse(

        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String name,

        @JsonProperty("country")
        String country,

        @JsonProperty("items")
        List<Item> items
) {
}