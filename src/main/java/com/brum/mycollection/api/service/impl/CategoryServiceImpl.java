package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.domain.category.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.mapper.CategoryMapper;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        log.info("Criando Categoria {}.", categoryRequest.name());

        validateCategoryName(categoryRequest);
        verifyIfCategoryNameExists(categoryRequest);

        Category category = CategoryMapper.toEntity(categoryRequest);

        this.categoryRepository.save(category);
        log.info("Categoria {} salva com sucesso!", category.getName());
        CategoryResponse categoryResponse = CategoryMapper.toResponse(category);
        return categoryResponse;
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        log.info("Atualizando Categoria {}.", categoryRequest.name());

        this.findById(id);
        Category category = CategoryMapper.toEntity(categoryRequest);
        category.setId(id);
        this.categoryRepository.save(category);

        CategoryResponse categoryResponse = CategoryMapper.toResponse(category);
        log.info("Categoria atualizada com sucesso!");
        return categoryResponse;
    }

    @Override
    public CategoryResponse findById(Long id) {
        log.info("Buscando categoria...");
        Optional<Category> category = this.categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new CategoryException("Categoria não encontrada.", HttpStatus.NOT_FOUND);
        }

        CategoryResponse categoryResponse = CategoryMapper.toResponse(category.get());

        log.info("Categoria {} encontrada.", category.get().getName());
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> list() {
        log.info("Listando categorias...");
        List<Category> categories = this.categoryRepository.findAllByOrderByNameAsc();
        return CategoryMapper.toResponseList(categories);

    }

    @Override
    public void delete(Long id) {
        CategoryResponse categoriaResponse = this.findById(id);

        log.info("Deletando categoria {} ...", categoriaResponse.name());
        this.categoryRepository.deleteById(id);
        log.info("Categoria {} deletada com sucesso!", categoriaResponse.name());
    }

    private void verifyIfCategoryNameExists(CategoryRequest categoryRequest) {
        log.info("Validando se categoria {} existe.", categoryRequest.name());
        boolean categoryExistsByName = categoryRepository.existsCategoryByName(categoryRequest.name());
        if (categoryExistsByName) {
            throw new CategoryException("Categoria já existe.", HttpStatus.CONFLICT);
        }
        log.info("A categoria {} foi validada.", categoryRequest.name());
    }

    private void validateCategoryName(CategoryRequest categoryRequest) {
        if (categoryRequest.name() == null || categoryRequest.name().equals("")) {
            throw new CategoryException("O nome da categoria deve ser preenchida.", HttpStatus.BAD_REQUEST);
        }
    }
}