package com.brum.mycollection.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryResponse(@JsonProperty("id") Long id, @JsonProperty("name") String name) {

//    @JsonIgnore
//    @JsonProperty("id")
//    private Long id;
//
//    @JsonProperty("name")
//    @NotBlank(message = "Informe a descrição da Categoria")
//    private String name;
}