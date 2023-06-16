package com.brum.mycollection.api.model.response;

public record ArtistItemDetailsResponse(
    Long id,
    String name,
    String country,
    String title,
    String genre,
    String category,
    Integer releaseYear
) {}