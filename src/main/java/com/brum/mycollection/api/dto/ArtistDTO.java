package com.brum.mycollection.api.dto;

import com.brum.mycollection.api.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {

    private Long id;

    @NotBlank(message = "Informe a Banda")
    private String name;

    @NotBlank(message = "Informe o País")
    private String country;

}