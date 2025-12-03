package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;

import java.util.List;

public record ItemResponse(
        Long id,
        String title,
        Integer releaseYear,
        String genre,
        Category category,
        Artist artist,
        String coverImagePath,
        // Novos campos para integração com Discogs
        LabelResponse label,
        Integer discogsReleaseId,
        Integer discogsMasterId,
        String discogsImageUrl,
        List<TrackResponse> tracks,
        List<CreditResponse> credits
) {}
