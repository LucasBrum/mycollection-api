package com.brum.mycollection.api.model.request;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ItemRequest (

        @JsonProperty("title")
        @NotBlank(message = "Informe o Título")
        String title,

        @JsonProperty("releaseYear")
        Integer releaseYear,

        @JsonProperty("genre")
        String genre,

        @JsonProperty("category")
        Category category,

        @JsonProperty("artist")
        Artist artist,

        @JsonProperty("artistName")
        String artistName,

        @JsonProperty("artistCountry")
        String artistCountry,

        @JsonProperty("coverImage")
        byte[] coverImage,

        // Novos campos para integração com Discogs

        @JsonProperty("labelId")
        Long labelId,

        @JsonProperty("labelName")
        String labelName,

        @JsonProperty("discogsLabelId")
        Integer discogsLabelId,

        @JsonProperty("discogsReleaseId")
        Integer discogsReleaseId,

        @JsonProperty("discogsMasterId")
        Integer discogsMasterId,

        @JsonProperty("discogsImageUrl")
        String discogsImageUrl,

        @JsonProperty("tracks")
        List<TrackRequest> tracks,

        @JsonProperty("credits")
        List<CreditRequest> credits

) {}
