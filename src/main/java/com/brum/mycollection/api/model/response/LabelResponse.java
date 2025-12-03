package com.brum.mycollection.api.model.response;

public record LabelResponse(
        Long id,
        String name,
        Integer discogsId
) {}
