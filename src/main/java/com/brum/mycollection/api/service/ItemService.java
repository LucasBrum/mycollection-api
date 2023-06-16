package com.brum.mycollection.api.service;

import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemResponseWithCoverImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException;

    byte[] findCoverImageById(Long id);

    List<ItemResponse> listAll();

    List<ItemResponseWithCoverImage> listAllWithCoverImage();

}
