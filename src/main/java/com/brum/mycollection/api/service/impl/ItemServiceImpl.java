package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.domain.item.Item;
import com.brum.mycollection.api.domain.item.validations.ItemValidator;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.mapper.ItemMapper;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
import com.brum.mycollection.api.repository.ItemRepository;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.util.ImageUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final List<ItemValidator> validators;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, List<ItemValidator> validators) {
        this.itemRepository = itemRepository;
        this.validators = validators;
    }

    @Override
    public ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException {
        validators.forEach(v -> v.validate(itemRequest));
        try {
            Item item = ItemMapper.toEntity(itemRequest);
            item.setCoverImage(ImageUtility.compressImage(file.getBytes()));

            this.itemRepository.save(item);

            log.info("Item {} created successfully", item.getTitle());

            ItemResponse itemResponse = ItemMapper.toResponse(item);

            return itemResponse;
        } catch (Exception e) {
            throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] findCoverImageById(Long id) {
        log.info("Searching image by id {}.", id);

        try {
            Optional<Item> item = this.itemRepository.findById(id);
            if (item.isEmpty()) {
                throw new ArtistException("Item não encontrado.", HttpStatus.NOT_FOUND);
            }

            byte[] coverImage = item.get().getCoverImage();
            log.info("Cover Image for Item {} was found.", item.get().getTitle());
            return coverImage;

        } catch (ArtistException aex) {
            throw aex;
        } catch (Exception e) {
            throw new ArtistException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ItemResponse> listAll() {
        log.info("Listing all Items.");
        try {
            List<Item> itemList = this.itemRepository.findAll();
            return ItemMapper.toResponseList(itemList);
        } catch (Exception e) {
            throw new ArtistException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ItemWithCoverImageResponse> listAllWithCoverImage() {
        log.info("Listing all items with cover images.");
        try {
            List<Item> itemList = this.itemRepository.findAll();
            return ItemMapper.toResponseListWithCoverImage(itemList);
        } catch (Exception e) {
            throw new ArtistException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}