package com.brum.mycollection.api.service;

import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ArtistResponse;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException;
    ItemResponse update(Long id, ItemRequest itemRequest, MultipartFile coverImageFile) throws IOException;
    ItemResponse findById(Long id);

    byte[] findCoverImageById(Long id);

    List<ItemResponse> listAll();

    List<ItemWithCoverImageResponse> listAllWithCoverImage();

}