package com.brum.mycollection.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record UserRequest(
        @JsonProperty("name")
        @NotBlank(message = "Informe o Username")
        String username,

        @JsonProperty("country")
        @NotBlank(message = "Informe o Email")
        String email,

        @JsonProperty("country")
        @NotBlank(message = "Informe a Senha")
        String password
){}