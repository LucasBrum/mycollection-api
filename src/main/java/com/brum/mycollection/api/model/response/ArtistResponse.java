package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtistResponse {

    @JsonIgnore
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("items")
    private List<Item> items;


}