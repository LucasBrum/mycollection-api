package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.model.request.ArtistRequest;
import com.brum.mycollection.api.model.response.ArtistResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtistMapper {

    public static ArtistResponse toResponse(Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse(
                artist.getId(),
                artist.getName(),
                artist.getCountry(),
                null

        );
        return artistResponse;
    }

    public static Artist toEntity(ArtistRequest artistRequest) {
        Artist artist = Artist.builder().name(artistRequest.name()).country(artistRequest.country()).build();

        return artist;
    }

    public static List<ArtistResponse> toResponseList(List<Artist> artistList) {
        List<ArtistResponse> artistResponseList = artistList.stream().map(ArtistMapper::toResponse).toList();

        return artistResponseList;
    }

}