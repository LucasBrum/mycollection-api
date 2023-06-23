package com.brum.mycollection.api.validations.item;

import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.request.ItemRequest;
import com.brum.mycollection.api.repository.ItemRepository;
import com.brum.mycollection.api.validations.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateIfItemExistsByTitle implements Validator<ItemRequest> {

    private final ItemRepository itemRepository;

    @Autowired
    public ValidateIfItemExistsByTitle(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void validate(ItemRequest itemRequest) {
        log.info("Checking if the Item {} already exists.", itemRequest.title());
        Boolean isItemFounded = itemRepository.existsArtistByTitle(itemRequest.title());

        if (isItemFounded) {
            throw new ArtistException("Item already registered.", HttpStatus.BAD_REQUEST);
        }
    }
}