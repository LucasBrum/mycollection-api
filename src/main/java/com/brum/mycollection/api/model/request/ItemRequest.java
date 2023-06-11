package com.brum.mycollection.api.model.request;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemRequest {

    @JsonProperty("title")
    @NotBlank(message = "Informe o Título")
    private String title;

    @JsonProperty("releaseYear")
    @NotBlank(message = "Informe o ano de Lançamento")
    private Integer releaseYear;

    @JsonProperty("genre")
    @NotBlank(message = "Informe o Gênero")
    private String genre;

    @JsonProperty("category")
    @NotBlank(message = "Informe a Categoria")
    private Category category;

    @JsonProperty("artist")
    @NotBlank(message = "Informe o Artista")
    private Artist artist;

    @JsonProperty("coverImage")
    private byte[] coverImage;

}