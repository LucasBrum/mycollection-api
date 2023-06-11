package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.repository.ItemRepository;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.util.ImageUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException {
        Boolean isArtistFounded = itemRepository.existsArtistByTitle(itemRequest.getTitle());

        if (isArtistFounded) {
            throw new ArtistException("Album já cadastrado.", HttpStatus.BAD_REQUEST);
        }

        try {
            Item item = this.modelMapper.map(itemRequest, Item.class);
            item.setCoverImage(ImageUtility.compressImage(file.getBytes()));
            this.itemRepository.save(item);
            ItemResponse itemResponse = modelMapper.map(item, ItemResponse.class);

            return itemResponse;
        } catch (Exception e) {
            throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] findCoverImageById(Long id) {
        try {
            Optional<Item> item = this.itemRepository.findById(id);
            if (item.isPresent()) {
                byte[] coverImage = item.get().getCoverImage();
                return coverImage;
            }

            throw new ArtistException("Item não encontrado.", HttpStatus.NOT_FOUND);
        } catch (ArtistException aex) {
            throw aex;
        } catch (Exception e) {
            throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}