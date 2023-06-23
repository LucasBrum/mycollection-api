package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.util.Constants;
import com.brum.mycollection.api.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
        headers.setContentType(MediaType.valueOf(Constants.IMAGE_PNG));

        return new ResponseEntity<>(ImageUtility.decompressImage(coverImage), headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<List<ItemResponse>>> listAll() {
        List<ItemResponse> itemResponseList = this.itemService.listAll();

        Response<List<ItemResponse>> response = new Response<>();
        response.setData(itemResponseList);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cover-images")
    public ResponseEntity<Response<List<ItemWithCoverImageResponse>>> listAllWithCoverImage() {
        List<ItemWithCoverImageResponse> itemWithCoverImageResponses = this.itemService.listAllWithCoverImage();

        Response<List<ItemWithCoverImageResponse>> response = new Response<>();
        response.setData(itemWithCoverImageResponses);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}