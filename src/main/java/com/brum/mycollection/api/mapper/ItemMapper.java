package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemResponseWithCoverImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {

    public static ItemResponse toResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse(
                item.getId(),
                item.getTitle(),
                item.getReleaseYear(),
                item.getGenre(),
                item.getCategory(),
                item.getArtist()

        );
        return itemResponse;
    }

    public static ItemResponseWithCoverImage toResponseWithCoverImage(Item item) {
        ItemResponseWithCoverImage itemResponseWithCoverImage = new ItemResponseWithCoverImage(
                item.getId(),
                item.getTitle(),
                item.getReleaseYear(),
                item.getGenre(),
                item.getCategory(),
                item.getArtist(),
                item.getCoverImage()

        );
        return itemResponseWithCoverImage;
    }

    public static Item toEntity(ItemRequest itemRequest) {
        Item item = Item.builder()
                .title(itemRequest.title())
                .genre(itemRequest.genre())
                .releaseYear(itemRequest.releaseYear())
                .category(itemRequest.category())
                .artist(itemRequest.artist())
                .coverImage(itemRequest.coverImage())
                .build();

        return item;
    }

    public static List<ItemResponse> toResponseList(List<Item> ItemList) {
        List<ItemResponse> itemResponseList = ItemList.stream().map(ItemMapper::toResponse).toList();

        return itemResponseList;
    }

    public static List<ItemResponseWithCoverImage> toResponseListWithCoverImage(List<Item> ItemList) {
        List<ItemResponseWithCoverImage> itemResponseWithCoverImages = ItemList.stream().map(ItemMapper::toResponseWithCoverImage).toList();

        return itemResponseWithCoverImages;
    }

}