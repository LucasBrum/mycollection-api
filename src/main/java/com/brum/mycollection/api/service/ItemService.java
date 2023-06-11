package com.brum.mycollection.api.service;

import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ItemService {

    ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException;

    byte[] findCoverImageById(Long id);
}
