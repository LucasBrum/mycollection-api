package com.brum.mycollection.api.validations.category;

import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.validations.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateIfCategoryNameExists implements Validator<CategoryRequest> {

    private final CategoryRepository categoryRepository;

    public ValidateIfCategoryNameExists(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void validate(CategoryRequest categoryRequest) {
        log.info(Messages.VALIDATION_IF_CATEGORY_ALREADY_EXISTS, categoryRequest.name());
        boolean categoryExistsByName = categoryRepository.existsCategoryByName(categoryRequest.name());
        if (categoryExistsByName) {
            throw new CategoryException(Messages.CATEGORY_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
        log.info(Messages.CATEGORY_SUCCESSFULLY_VALIDATED, categoryRequest.name());
    }
}