package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
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

    public static ItemWithCoverImageResponse toResponseWithCoverImage(Item item) {
        ItemWithCoverImageResponse itemWithCoverImageResponse = new ItemWithCoverImageResponse(
                item.getId(),
                item.getTitle(),
                item.getReleaseYear(),
                item.getGenre(),
                item.getCategory(),
                item.getArtist(),
                item.getCoverImage()

        );
        return itemWithCoverImageResponse;
    }

    public static Item toEntity(ItemRequest itemRequest) {
        Item item = Item.builder()
                .title(itemRequest.title())
                .genre(itemRequest.genre())
                .releaseYear(itemRequest.releaseYear())
                .category(itemRequest.category())
                .artist(itemRequest.artist())
//                .coverImage(itemRequest.coverImage())
                .build();

        return item;
    }

    public static List<ItemResponse> toResponseList(List<Item> ItemList) {
        List<ItemResponse> itemResponseList = ItemList.stream().map(ItemMapper::toResponse).toList();

        return itemResponseList;
    }

    public static List<ItemWithCoverImageResponse> toResponseListWithCoverImage(List<Item> ItemList) {
        List<ItemWithCoverImageResponse> itemWithCoverImageResponses = ItemList.stream().map(ItemMapper::toResponseWithCoverImage).toList();

        return itemWithCoverImageResponses;
    }

}