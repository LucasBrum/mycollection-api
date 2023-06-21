package com.brum.mycollection.api.model.response;

public record UserResponse(

        Long id,
        String username,
        String email,
        String password
){}