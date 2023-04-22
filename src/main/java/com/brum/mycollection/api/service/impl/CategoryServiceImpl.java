package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.CategoryDTO;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.mapper = new ModelMapper();
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        try {
            Category category = this.mapper.map(categoryRequest, Category.class);

            validateCategoryName(categoryRequest);
            verifyIfCategoryNameExists(categoryRequest);

            this.categoryRepository.save(category);
            var categoryResponse = mapper.map(category, CategoryResponse.class);
            return categoryResponse;
        } catch (CategoryException cex) {
            throw cex;
        } catch (Exception e) {
            throw new CategoryException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = this.mapper.map(categoryRequest, Category.class);
        this.findById(id);
        try {
            category.setId(id);
            this.categoryRepository.save(category);
            var categoryResponse = mapper.map(category, CategoryResponse.class);

            return categoryResponse;
        } catch (Exception e) {
            throw new CategoryException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoryResponse findById(Long id) {
        try {
            Optional<Category> category = this.categoryRepository.findById(id);
            if (category.isPresent()) {
                CategoryResponse categoryResponse = mapper.map(category.get(), CategoryResponse.class);
                return categoryResponse;
            }

            throw new CategoryException("Categoria não encontrada.", HttpStatus.NOT_FOUND);

        } catch (CategoryException cex) {
            throw cex;
        } catch (Exception e) {
            throw new CategoryException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CategoryResponse> list() {
        try {
            List<Category> categories = this.categoryRepository.findAllByOrderByNameAsc();
            return this.mapper.map(categories, new TypeToken<List<CategoryResponse>>() {}.getType());
        } catch (Exception e) {
            throw new CategoryException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            this.findById(id);

            this.categoryRepository.deleteById(id);
        } catch (CategoryException ce) {
            throw new CategoryException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void verifyIfCategoryNameExists(CategoryRequest categoryRequest) {
        Boolean categoryExistsByName = categoryRepository.existsByName(categoryRequest.getName());
        if (categoryExistsByName) {
            throw new CategoryException("Categoria já existe.", HttpStatus.CONFLICT);
        }
    }

    private void validateCategoryName(CategoryRequest categoryRequest) {
        if (categoryRequest.getName() == null || categoryRequest.getName().equals("")) {
            throw new CategoryException("O nome da categoria deve ser preenchida.", HttpStatus.BAD_REQUEST);
        }
    }
}