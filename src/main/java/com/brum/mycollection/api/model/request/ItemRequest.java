package com.brum.mycollection.api.model.request;

import com.brum.mycollection.api.domain.artist.Artist;
import com.brum.mycollection.api.domain.category.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record ItemRequest (

        @JsonProperty("title")
        @NotBlank(message = "Informe o Título")
        String title,

        @JsonProperty("releaseYear")
        @NotBlank(message = "Informe o ano de Lançamento")
        Integer releaseYear,

        @JsonProperty("genre")
        @NotBlank(message = "Informe o Gênero")
        String genre,

        @JsonProperty("category")
        @NotBlank(message = "Informe a Categoria")
        Category category,

        @JsonProperty("artist")
        @NotBlank(message = "Informe o Artista")
        Artist artist,

        @JsonProperty("coverImage")
        byte[] coverImage

) {}