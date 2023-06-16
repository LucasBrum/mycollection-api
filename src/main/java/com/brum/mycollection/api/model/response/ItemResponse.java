package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Category;

public record ItemResponse (
    String title,
    Integer releaseYear,
    String genre,
    Category category,
    byte[] coverImage
) {}