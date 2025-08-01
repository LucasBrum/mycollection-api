package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;

import java.util.List;

public class ItemMapper {

    public static Item toEntity(ItemRequest itemRequest) {
        return Item.builder()
                .title(itemRequest.title())
                .releaseYear(itemRequest.releaseYear())
                .genre(itemRequest.genre())
                .category(itemRequest.category())
                .artist(itemRequest.artist())
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
                item.getCoverImagePath()
        );
    }

    public static List<ItemResponse> toResponseList(List<Item> items) {
        return items.stream()
                .map(ItemMapper::toResponse)
                .toList();
    }
}