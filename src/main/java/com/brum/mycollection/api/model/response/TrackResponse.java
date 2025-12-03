package com.brum.mycollection.api.model.response;

public record TrackResponse(
        Long id,
        String position,
        String title,
        String duration,
        Integer trackOrder
) {}
