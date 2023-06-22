package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.domain.artist.Artist;
import com.brum.mycollection.api.domain.category.Category;

public record ItemWithCoverImageResponse(
    Long id,
    String title,
    Integer releaseYear,
    String genre,
    Category category,
    Artist artist,
    byte[] coverImage
) {}