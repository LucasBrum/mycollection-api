package com.brum.mycollection.api.domain.item.validations;

import com.brum.mycollection.api.model.request.ItemRequest;

public interface ItemValidator {

    void validate(ItemRequest itemRequest);
}
