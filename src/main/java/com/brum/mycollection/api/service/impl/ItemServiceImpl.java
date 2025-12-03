package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Credit;
import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.entity.Label;
import com.brum.mycollection.api.entity.Track;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.mapper.ItemMapper;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.model.response.ItemResponse;
import com.brum.mycollection.api.model.response.ItemWithCoverImageResponse;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.repository.ItemRepository;
import com.brum.mycollection.api.repository.LabelRepository;
import com.brum.mycollection.api.service.ItemService;
import com.brum.mycollection.api.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final LabelRepository labelRepository;
    private final ArtistRepository artistRepository;
    private final StorageService storageService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                          LabelRepository labelRepository,
                          ArtistRepository artistRepository,
                          StorageService storageService) {
        this.itemRepository = itemRepository;
        this.labelRepository = labelRepository;
        this.artistRepository = artistRepository;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public ItemResponse create(ItemRequest itemRequest, MultipartFile file) throws IOException {
        Boolean isAlbumExists = itemRepository.existsArtistByTitle(itemRequest.title());

        if (isAlbumExists) {
            throw new ArtistException("Album já cadastrado.", HttpStatus.BAD_REQUEST);
        }

        try {
            Item item = ItemMapper.toEntity(itemRequest);

            // Processa o Artista (cria se não existir)
            Artist artist = processArtist(itemRequest);
            item.setArtist(artist);

            // Processa a Label (gravadora)
            Label label = processLabel(itemRequest);
            item.setLabel(label);

            // Se tem URL de imagem do Discogs, usa ela
            // Senão, faz upload para S3 se houver arquivo
            if (itemRequest.discogsImageUrl() != null && !itemRequest.discogsImageUrl().isEmpty()) {
                item.setDiscogsImageUrl(itemRequest.discogsImageUrl());
                // Não precisa de coverImagePath se usar Discogs
            } else if (file != null && !file.isEmpty()) {
                String filename = storageService.storeFile(file, "cvr-" + item.getTitle().toLowerCase().replace(" ", "-"));
                item.setCoverImagePath(filename);
            }

            // Salva o item primeiro para ter o ID
            item = this.itemRepository.save(item);

            // Processa as tracks
            if (itemRequest.tracks() != null && !itemRequest.tracks().isEmpty()) {
                List<Track> tracks = ItemMapper.toTrackEntityList(itemRequest.tracks(), item);
                item.getTracks().addAll(tracks);
            }

            // Processa os credits
            if (itemRequest.credits() != null && !itemRequest.credits().isEmpty()) {
                List<Credit> credits = ItemMapper.toCreditEntityList(itemRequest.credits(), item);
                item.getCredits().addAll(credits);
            }

            // Salva novamente com tracks e credits
            item = this.itemRepository.save(item);

            return ItemMapper.toResponse(item);
        } catch (ArtistException e) {
            throw e;
        } catch (Exception e) {
            throw new ArtistException("Erro interno: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
                            // Prioriza imagem do Discogs, senão usa S3
                            item.getDiscogsImageUrl() != null ? item.getDiscogsImageUrl() : item.getCoverImagePath()))
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
                item.getDiscogsImageUrl() != null ? item.getDiscogsImageUrl() : item.getCoverImagePath()
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
    @Transactional
    public ItemResponse update(Long id, ItemRequest itemRequest, MultipartFile file) throws IOException {
        try {
            Optional<Item> existingItemOptional = itemRepository.findById(id);
            if (!existingItemOptional.isPresent()) {
                throw new ArtistException("Item não encontrado", HttpStatus.NOT_FOUND);
            }

            Item existingItem = existingItemOptional.get();
            Item updatedItem = ItemMapper.toEntity(itemRequest);
            updatedItem.setId(id);

            // Processa a Label
            Label label = processLabel(itemRequest);
            updatedItem.setLabel(label);

            // Gerencia imagens
            if (itemRequest.discogsImageUrl() != null && !itemRequest.discogsImageUrl().isEmpty()) {
                updatedItem.setDiscogsImageUrl(itemRequest.discogsImageUrl());
                // Se tinha imagem local, pode deletar
                if (existingItem.getCoverImagePath() != null) {
                    storageService.deleteFile(existingItem.getCoverImagePath());
                }
                updatedItem.setCoverImagePath(null);
            } else if (file != null && !file.isEmpty()) {
                // Se houver uma imagem antiga, deleta
                if (existingItem.getCoverImagePath() != null) {
                    storageService.deleteFile(existingItem.getCoverImagePath());
                }
                String filename = storageService.storeFile(file, "cvr-" + updatedItem.getTitle().toLowerCase().replace(" ", "-"));
                updatedItem.setCoverImagePath(filename);
            } else {
                // Mantém as imagens antigas
                updatedItem.setCoverImagePath(existingItem.getCoverImagePath());
                updatedItem.setDiscogsImageUrl(existingItem.getDiscogsImageUrl());
            }

            // Salva o item
            updatedItem = itemRepository.save(updatedItem);

            // Limpa tracks e credits antigos e adiciona novos
            updatedItem.getTracks().clear();
            updatedItem.getCredits().clear();

            if (itemRequest.tracks() != null && !itemRequest.tracks().isEmpty()) {
                List<Track> tracks = ItemMapper.toTrackEntityList(itemRequest.tracks(), updatedItem);
                updatedItem.getTracks().addAll(tracks);
            }

            if (itemRequest.credits() != null && !itemRequest.credits().isEmpty()) {
                List<Credit> credits = ItemMapper.toCreditEntityList(itemRequest.credits(), updatedItem);
                updatedItem.getCredits().addAll(credits);
            }

            updatedItem = itemRepository.save(updatedItem);
            return ItemMapper.toResponse(updatedItem);

        } catch (ArtistException e) {
            throw e;
        } catch (Exception e) {
            throw new ArtistException("Erro interno ao atualizar item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            Optional<Item> itemOptional = itemRepository.findById(id);
            if (!itemOptional.isPresent()) {
                throw new ArtistException("Item não encontrado", HttpStatus.NOT_FOUND);
            }

            Item item = itemOptional.get();

            // Se houver uma imagem local, deleta
            if (item.getCoverImagePath() != null) {
                storageService.deleteFile(item.getCoverImagePath());
            }

            // Tracks e credits serão deletados automaticamente pelo cascade
            itemRepository.delete(item);
        } catch (ArtistException e) {
            throw e;
        } catch (Exception e) {
            throw new ArtistException("Erro interno ao deletar item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Processa a Label (gravadora) do request.
     * Se já existe uma label com o mesmo discogsId ou nome, reutiliza.
     * Senão, cria uma nova.
     */
    private Label processLabel(ItemRequest itemRequest) {
        // Se não tem informação de label, retorna null
        if ((itemRequest.labelName() == null || itemRequest.labelName().isEmpty())
            && itemRequest.labelId() == null
            && itemRequest.discogsLabelId() == null) {
            return null;
        }

        // Se já tem um labelId, busca a label existente
        if (itemRequest.labelId() != null) {
            return labelRepository.findById(itemRequest.labelId()).orElse(null);
        }

        // Tenta encontrar por discogsId
        if (itemRequest.discogsLabelId() != null) {
            Optional<Label> existingLabel = labelRepository.findByDiscogsId(itemRequest.discogsLabelId());
            if (existingLabel.isPresent()) {
                return existingLabel.get();
            }
        }

        // Tenta encontrar por nome
        if (itemRequest.labelName() != null && !itemRequest.labelName().isEmpty()) {
            Optional<Label> existingLabel = labelRepository.findByNameIgnoreCase(itemRequest.labelName());
            if (existingLabel.isPresent()) {
                return existingLabel.get();
            }
        }

        // Cria uma nova label
        if (itemRequest.labelName() != null && !itemRequest.labelName().isEmpty()) {
            Label newLabel = Label.builder()
                    .name(itemRequest.labelName())
                    .discogsId(itemRequest.discogsLabelId())
                    .build();
            return labelRepository.save(newLabel);
        }

        return null;
    }

    /**
     * Processa o Artista do request.
     * Se já existe um artista com o mesmo ID ou nome, reutiliza.
     * Senão, cria um novo.
     */
    private Artist processArtist(ItemRequest itemRequest) {
        // Se já tem um artista com ID, usa ele
        if (itemRequest.artist() != null && itemRequest.artist().getId() != null) {
            return artistRepository.findById(itemRequest.artist().getId()).orElse(null);
        }

        // Se tem artistName, tenta encontrar por nome ou cria novo
        if (itemRequest.artistName() != null && !itemRequest.artistName().isEmpty()) {
            Optional<Artist> existingArtist = artistRepository.findByNameIgnoreCase(itemRequest.artistName());
            if (existingArtist.isPresent()) {
                return existingArtist.get();
            }

            // Cria novo artista com país (se disponível)
            Artist newArtist = Artist.builder()
                    .name(itemRequest.artistName())
                    .country(itemRequest.artistCountry())
                    .build();
            return artistRepository.save(newArtist);
        }

        return null;
    }
}
