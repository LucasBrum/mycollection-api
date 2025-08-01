package com.brum.mycollection.api.model.response;

public record ItemWithCoverImageResponse(
    Long id,
    String title,
    Integer releaseYear,
    String genre,
    String coverImagePath
) {}