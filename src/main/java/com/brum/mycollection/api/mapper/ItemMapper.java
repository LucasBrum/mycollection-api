package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.entity.Credit;
import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.entity.Label;
import com.brum.mycollection.api.entity.Track;
import com.brum.mycollection.api.model.request.CreditRequest;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.request.TrackRequest;
import com.brum.mycollection.api.model.response.CreditResponse;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.LabelResponse;
import com.brum.mycollection.api.model.response.TrackResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public static Item toEntity(ItemRequest itemRequest) {
        return Item.builder()
                .title(itemRequest.title())
                .releaseYear(itemRequest.releaseYear())
                .genre(itemRequest.genre())
                .category(itemRequest.category())
                .artist(itemRequest.artist())
                .discogsReleaseId(itemRequest.discogsReleaseId())
                .discogsMasterId(itemRequest.discogsMasterId())
                .discogsImageUrl(itemRequest.discogsImageUrl())
                .build();
    }

    public static ItemResponse toResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getTitle(),
                item.getReleaseYear(),
                item.getGenre(),
                item.getCategory(),
                item.getArtist(),
                item.getCoverImagePath(),
                toLabelResponse(item.getLabel()),
                item.getDiscogsReleaseId(),
                item.getDiscogsMasterId(),
                item.getDiscogsImageUrl(),
                toTrackResponseList(item.getTracks()),
                toCreditResponseList(item.getCredits())
        );
    }

    public static List<ItemResponse> toResponseList(List<Item> items) {
        return items.stream()
                .map(ItemMapper::toResponse)
                .toList();
    }

    // Mapper para Label
    public static LabelResponse toLabelResponse(Label label) {
        if (label == null) {
            return null;
        }
        return new LabelResponse(
                label.getId(),
                label.getName(),
                label.getDiscogsId()
        );
    }

    // Mapper para Track
    public static Track toTrackEntity(TrackRequest trackRequest, Item item) {
        return Track.builder()
                .item(item)
                .position(trackRequest.position())
                .title(trackRequest.title())
                .duration(trackRequest.duration())
                .trackOrder(trackRequest.trackOrder())
                .build();
    }

    public static List<Track> toTrackEntityList(List<TrackRequest> trackRequests, Item item) {
        if (trackRequests == null || trackRequests.isEmpty()) {
            return new ArrayList<>();
        }
        return trackRequests.stream()
                .map(tr -> toTrackEntity(tr, item))
                .collect(Collectors.toList());
    }

    public static TrackResponse toTrackResponse(Track track) {
        return new TrackResponse(
                track.getId(),
                track.getPosition(),
                track.getTitle(),
                track.getDuration(),
                track.getTrackOrder()
        );
    }

    public static List<TrackResponse> toTrackResponseList(List<Track> tracks) {
        if (tracks == null || tracks.isEmpty()) {
            return new ArrayList<>();
        }
        return tracks.stream()
                .map(ItemMapper::toTrackResponse)
                .toList();
    }

    // Mapper para Credit
    public static Credit toCreditEntity(CreditRequest creditRequest, Item item) {
        return Credit.builder()
                .item(item)
                .name(creditRequest.name())
                .role(creditRequest.role())
                .discogsArtistId(creditRequest.discogsArtistId())
                .build();
    }

    public static List<Credit> toCreditEntityList(List<CreditRequest> creditRequests, Item item) {
        if (creditRequests == null || creditRequests.isEmpty()) {
            return new ArrayList<>();
        }
        return creditRequests.stream()
                .map(cr -> toCreditEntity(cr, item))
                .collect(Collectors.toList());
    }

    public static CreditResponse toCreditResponse(Credit credit) {
        return new CreditResponse(
                credit.getId(),
                credit.getName(),
                credit.getRole()
        );
    }

    public static List<CreditResponse> toCreditResponseList(List<Credit> credits) {
        if (credits == null || credits.isEmpty()) {
            return new ArrayList<>();
        }
        return credits.stream()
                .map(ItemMapper::toCreditResponse)
                .toList();
    }
}
