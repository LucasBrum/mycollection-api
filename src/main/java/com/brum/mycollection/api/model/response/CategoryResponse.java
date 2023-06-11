package com.brum.mycollection.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    @JsonIgnore
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank(message = "Informe a descrição da Categoria")
    private String name;
}