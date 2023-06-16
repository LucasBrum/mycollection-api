package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;

public record ItemResponseWithCoverImage(
    Long id,
    String title,
    Integer releaseYear,
    String genre,
    Category category,
    Artist artist,
    byte[] coverImage
) {}