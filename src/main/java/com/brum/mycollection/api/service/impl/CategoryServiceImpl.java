package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.mapper.CategoryMapper;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.CategoryService;
import com.brum.mycollection.api.validations.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private List<Validator<CategoryRequest>> validators;

    public CategoryServiceImpl(CategoryRepository categoryRepository, List<Validator<CategoryRequest>> validators) {
        this.categoryRepository = categoryRepository;
        this.validators = validators;
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        log.info(Messages.CREATING_CATEGORY, categoryRequest.name());

        validators.forEach(v -> v.validate(categoryRequest));

        Category category = CategoryMapper.toEntity(categoryRequest);

        this.categoryRepository.save(category);
        log.info(Messages.CATEGORY_SUCCESSFULLY_SAVED, category.getName());
        CategoryResponse categoryResponse = CategoryMapper.toResponse(category);
        return categoryResponse;
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        log.info(Messages.UPDATING_CATEGORY, categoryRequest.name());

        this.findById(id);
        Category category = CategoryMapper.toEntity(categoryRequest);
        category.setId(id);
        this.categoryRepository.save(category);

        CategoryResponse categoryResponse = CategoryMapper.toResponse(category);
        log.info(Messages.CATEGORY_SUCCESSFULLY_UPDATED, category.getName());
        return categoryResponse;
    }

    @Override
    public CategoryResponse findById(Long id) {
        log.info(Messages.SEARCHING_CATEGORY);
        Optional<Category> category = this.categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new CategoryException("Category not found.", HttpStatus.NOT_FOUND);
        }

        CategoryResponse categoryResponse = CategoryMapper.toResponse(category.get());

        log.info(Messages.CATEGORY_FOUND, category.get().getName());
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> list() {
        log.info(Messages.LISTING_CATEGORIES);
        List<Category> categories = this.categoryRepository.findAllByOrderByNameAsc();
        return CategoryMapper.toResponseList(categories);

    }

    @Override
    public void delete(Long id) {
        CategoryResponse categoriaResponse = this.findById(id);

        log.info(Messages.DELETING_CATEGORY, categoriaResponse.name());
        this.categoryRepository.deleteById(id);
        log.info(Messages.CATEGORY_DELETING_SUCCESSFULLY, categoriaResponse.name());
    }

}