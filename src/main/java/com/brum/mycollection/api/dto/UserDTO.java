package com.brum.mycollection.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Informe o Username")
    private String username;

    @NotBlank(message = "Informe o Email")
    private String email;

    @NotBlank(message = "Informe a Senha")
    private String password;

}
