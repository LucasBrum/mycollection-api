package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.mapper.ItemMapper;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
import com.brum.mycollection.api.repository.ItemRepository;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.util.ImageUtility;
import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.validations.Validator;
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

    private final List<Validator<ItemRequest>> validators;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, List<Validator<ItemRequest>> validators) {
        this.itemRepository = itemRepository;
        this.validators = validators;
    }

    @Override
    public ItemResponse create(ItemRequest itemRequest, MultipartFile coverImageFile) throws IOException {
        log.info(Messages.CREATING_A_NEW_ITEM, itemRequest.title());
        validators.forEach(v -> v.validate(itemRequest));
        try {
            Item item = ItemMapper.toEntity(itemRequest);
            item.setCoverImage(ImageUtility.compressImage(coverImageFile.getBytes()));

            this.itemRepository.save(item);

            log.info(Messages.ITEM_CREATED_SUCCESSFULLY, item.getTitle());

            ItemResponse itemResponse = ItemMapper.toResponse(item);

            return itemResponse;
        } catch (Exception e) {
            throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemResponse update(Long id, ItemRequest itemRequest, MultipartFile coverImageFile) throws IOException {
        log.info(Messages.UPDATING_ITEM);
        Item item = ItemMapper.toEntity(itemRequest);

        validateCoverImageFile(id, coverImageFile, item);

        try {
            item.setId(id);
            this.itemRepository.save(item);
            ItemResponse itemResponse = ItemMapper.toResponse(item);
            log.info(Messages.ITEM_SUCCESSFULLY_UPDATED);
            return itemResponse;
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] findCoverImageById(Long id) {
        log.info(Messages.SEARCHING_IMAGE_BY_ID, id);

        try {
            Optional<Item> item = this.itemRepository.findById(id);
            if (item.isEmpty()) {
                throw new ArtistException("Item não encontrado.", HttpStatus.NOT_FOUND);
            }

            byte[] coverImage = item.get().getCoverImage();
            log.info(Messages.COVER_IMAGE_FOR_ITEM_WAS_FOUND, item.get().getTitle());
            return coverImage;

        } catch (ArtistException aex) {
            throw aex;
        } catch (Exception e) {
            throw new ArtistException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ItemResponse> listAll() {
        log.info(Messages.LISTING_ALL_ITEMS);
        try {
            List<Item> itemList = this.itemRepository.findAll();
            return ItemMapper.toResponseList(itemList);
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ItemWithCoverImageResponse> listAllWithCoverImage() {
        log.info(Messages.LISTING_ALL_ITEMS_WITH_COVER_IMAGES);
        try {
            List<Item> itemList = this.itemRepository.findAll();
            return ItemMapper.toResponseListWithCoverImage(itemList);
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemResponse findById(Long id) {
        log.info(Messages.SEARCHING_ITEM_BY_ID, id);
        try {
            Optional<Item> item = this.itemRepository.findById(id);
            if (item.isEmpty()) {
                throw new ArtistException(Messages.ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            ItemResponse itemResponse = ItemMapper.toResponse(item.get());
            return itemResponse;
        } catch (Exception e) {
            throw new ArtistException(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Long id) {
        ItemResponse itemResponse = this.findById(id);

        log.info(Messages.DELETING_ITEM_BY_ID, itemResponse.title());
        this.itemRepository.deleteById(id);
        log.info(Messages.ITEM_SUCCESSFULLY_DELETED, itemResponse.title());
    }

    private void validateCoverImageFile(Long id, MultipartFile coverImageFile, Item item) throws IOException {
        if (coverImageFile == null) {
            byte[] coverImageFileFound =  this.findCoverImageById(id);
            item.setCoverImage(coverImageFileFound);
        }

        if (coverImageFile != null) {
            item.setCoverImage(ImageUtility.compressImage(coverImageFile.getBytes()));
        }
    }

}