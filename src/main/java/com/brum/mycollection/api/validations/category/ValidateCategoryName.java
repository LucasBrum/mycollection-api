package com.brum.mycollection.api.validations.category;

import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.validations.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateCategoryName implements Validator<CategoryRequest> {

    public void validate(CategoryRequest categoryRequest) {
        if (categoryRequest.name() == null || categoryRequest.name().equals("")) {
            throw new CategoryException(Messages.THE_CATEGORY_NAME_MUST_BE_FILLED_IN, HttpStatus.BAD_REQUEST);
        }
    }
}