package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
        log.info("Criando Categoria {}.", categoryRequest.getName());
        Category category = this.mapper.map(categoryRequest, Category.class);

        validateCategoryName(categoryRequest);
        verifyIfCategoryNameExists(categoryRequest);

        this.categoryRepository.save(category);
        log.info("Categoria {} salva com sucesso!", category.getName());
        var categoryResponse = mapper.map(category, CategoryResponse.class);
        return categoryResponse;
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        log.info("Atualizando Categoria {}.", categoryRequest.getName());

        Category category = this.mapper.map(categoryRequest, Category.class);
        this.findById(id);
        category.setId(id);
        this.categoryRepository.save(category);

        var categoryResponse = mapper.map(category, CategoryResponse.class);
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

        CategoryResponse categoryResponse = mapper.map(category.get(), CategoryResponse.class);
        log.info("Categoria {} encontrada.", category.get().getName());
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> list() {
        log.info("Listando categorias...");
        List<Category> categories = this.categoryRepository.findAllByOrderByNameAsc();
        return this.mapper.map(categories, new TypeToken<List<CategoryResponse>>() {
        }.getType());

    }

    @Override
    public void delete(Long id) {
        CategoryResponse categoriaResponse = this.findById(id);

        log.info("Deletando categoria {} ...", categoriaResponse.getName());
        this.categoryRepository.deleteById(id);
        log.info("Categoria {} deletada com sucesso!", categoriaResponse.getName());
    }

    private void verifyIfCategoryNameExists(CategoryRequest categoryRequest) {
        log.info("Validando se categoria {} existe.", categoryRequest.getName());
        boolean categoryExistsByName = categoryRepository.existsByName(categoryRequest.getName());
        if (categoryExistsByName) {
            throw new CategoryException("Categoria já existe.", HttpStatus.CONFLICT);
        }
        log.info("A categoria {} foi validada.", categoryRequest.getName());
    }

    private void validateCategoryName(CategoryRequest categoryRequest) {
        if (categoryRequest.getName() == null || categoryRequest.getName().equals("")) {
            throw new CategoryException("O nome da categoria deve ser preenchida.", HttpStatus.BAD_REQUEST);
        }
    }
}