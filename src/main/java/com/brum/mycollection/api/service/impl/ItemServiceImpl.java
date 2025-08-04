package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.mapper.ItemMapper;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
import com.brum.mycollection.api.repository.ItemRepository;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.service.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final S3StorageService s3StorageService;
    
    @Value("${file.base-url}")
    private String fileBaseUrl;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, S3StorageService s3StorageService) {
        this.itemRepository = itemRepository;
        this.s3StorageService = s3StorageService;
    }

    @Override
    public ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException {
        Boolean isArtistFounded = itemRepository.existsArtistByTitle(itemRequest.title());

        if (isArtistFounded) {
            throw new ArtistException("Album já cadastrado.", HttpStatus.BAD_REQUEST);
        }

        try {
            Item item = ItemMapper.toEntity(itemRequest);
            
            // Store the file and get the filename
            String filename = s3StorageService.storeFile(file, "cvr-" + item.getTitle().toLowerCase().replace(" ", "-"));
            
            // Set the image path in S3
            item.setCoverImagePath(filename);
            
            this.itemRepository.save(item);
            return ItemMapper.toResponse(item);
        } catch (Exception e) {
            throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public List<ItemResponse> listAll() {
        try {
            List<Item> itemList = this.itemRepository.findAllByOrderByArtistNameAscReleaseYearAsc();
            return ItemMapper.toResponseList(itemList);
        } catch (Exception e) {
            throw new ArtistException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ItemWithCoverImageResponse> listAllWithCoverImage() {
        try {
            List<Item> items = itemRepository.findAllByOrderByArtistNameAscReleaseYearAsc();
            return items.stream()
                    .map(item -> new ItemWithCoverImageResponse(
                            item.getId(),
                            item.getTitle(),
                            item.getReleaseYear(),
                            item.getGenre(),
                            item.getCoverImagePath()))
                    .toList();
        } catch (Exception e) {
            throw new ArtistException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemWithCoverImageResponse findByCoverImagePath(String coverImagePath) {
        try {
            Item item = itemRepository.findByCoverImagePath(coverImagePath);
            if (item == null) {
                throw new ArtistException("Item não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ItemWithCoverImageResponse(
                item.getId(),
                item.getTitle(),
                item.getReleaseYear(),
                item.getGenre(),
                item.getCoverImagePath()
            );
        } catch (Exception e) {
            if (e instanceof ArtistException) {
                throw e;
            }
            throw new ArtistException("Erro interno ao buscar item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemResponse findById(Long id) {
        try {
            Optional<Item> itemOptional = this.itemRepository.findById(id);
            if (itemOptional.isPresent()) {
                return ItemMapper.toResponse(itemOptional.get());
            }
            throw new ArtistException("Item não encontrado", HttpStatus.NOT_FOUND);
        } catch (ArtistException e) {
            throw e;
        } catch (Exception e) {
            throw new ArtistException("Erro interno ao buscar item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemResponse update(Long id, ItemRequest itemRequest, MultipartFile file) throws IOException {
        try {
            Optional<Item> existingItemOptional = itemRepository.findById(id);
            if (!existingItemOptional.isPresent()) {
                throw new ArtistException("Item não encontrado", HttpStatus.NOT_FOUND);
            }

            Item existingItem = existingItemOptional.get();
            Item updatedItem = ItemMapper.toEntity(itemRequest);
            updatedItem.setId(id);

            // Mantém a imagem antiga se não foi enviada uma nova
            if (file != null) {
                // Se houver uma imagem antiga, deleta do S3
                if (existingItem.getCoverImagePath() != null) {
                    s3StorageService.deleteFile(existingItem.getCoverImagePath());
                }
                
                // Armazena a nova imagem
                String filename = s3StorageService.storeFile(file, "cvr-" + updatedItem.getTitle().toLowerCase().replace(" ", "-"));
                updatedItem.setCoverImagePath(filename);
            } else {
                updatedItem.setCoverImagePath(existingItem.getCoverImagePath());
            }

            updatedItem = itemRepository.save(updatedItem);
            return ItemMapper.toResponse(updatedItem);
            
        } catch (ArtistException e) {
            throw e;
        } catch (Exception e) {
            throw new ArtistException("Erro interno ao atualizar item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Optional<Item> itemOptional = itemRepository.findById(id);
            if (!itemOptional.isPresent()) {
                throw new ArtistException("Item não encontrado", HttpStatus.NOT_FOUND);
            }

            Item item = itemOptional.get();
            
            // Se houver uma imagem, deleta do S3
            if (item.getCoverImagePath() != null) {
                s3StorageService.deleteFile(item.getCoverImagePath());
            }

            itemRepository.delete(item);
        } catch (ArtistException e) {
            throw e;
        } catch (Exception e) {
            throw new ArtistException("Erro interno ao deletar item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}