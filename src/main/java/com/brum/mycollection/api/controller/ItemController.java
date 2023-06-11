package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Response<ItemResponse>> create(@RequestPart("item") ItemRequest itemRequest, @RequestPart("coverImage") MultipartFile coverImageFile) throws IOException {
        ItemResponse itemResponse = this.itemService.create(itemRequest, coverImageFile);
        Response<ItemResponse> response = new Response<>();
        response.setData(itemResponse);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/cover/{id}")
    public ResponseEntity<byte[]> getCoverFromAlbum(@PathVariable Long id) {
        byte[] coverImage = this.itemService.findCoverImageById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("image/png"));

        return new ResponseEntity<>(ImageUtility.decompressImage(coverImage), headers, HttpStatus.OK);
    }
}