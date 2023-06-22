package com.brum.mycollection.api.domain.item.validations;

import com.brum.mycollection.api.domain.item.Item;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateIfArtistExistsByTitle implements ItemValidator {

    private final ItemRepository itemRepository;

    @Autowired
    public ValidateIfArtistExistsByTitle(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void validate(ItemRequest itemRequest) {
        log.info("Checking if the Item {} already exists.", itemRequest.title());
        Boolean isArtistFounded = itemRepository.existsArtistByTitle(itemRequest.title());

        if (isArtistFounded) {
            throw new ArtistException("Item already registered", HttpStatus.BAD_REQUEST);
        }
    }
}