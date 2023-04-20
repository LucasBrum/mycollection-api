package com.brum.mycollection.api.model.response;

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
public class CategoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("descricao")
    @NotBlank(message = "Informe a descrição da Categoria")
    private String name;
}