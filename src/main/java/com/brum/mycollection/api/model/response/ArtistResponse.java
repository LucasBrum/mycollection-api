package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtistResponse {

    @JsonIgnore
    @JsonProperty("id")
    private Long id;

    @JsonProperty("band")
    @NotBlank(message = "Informe a Banda")
    private String band;

    @JsonProperty("title")
    @NotBlank(message = "Informe o Título")
    private String title;

    @JsonProperty("releaseYear")
    @NotBlank(message = "Informe o Ano de Lançamento")
    private Integer releaseYear;

    @JsonProperty("country")
    @NotBlank(message = "Informe o País")
    private String country;

    @JsonProperty("genre")
    @NotBlank(message = "Informe o Gênero")
    private String genre;

    @JsonProperty("category")
    @NotBlank(message = "Informe a Categoria")
    private Category category;

}