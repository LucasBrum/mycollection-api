package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.CategoryDTO;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.CategoryException;
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
    public CategoryDTO create(CategoryDTO categoryDTO) {
        try {
            Category category = this.mapper.map(categoryDTO, Category.class);

            Boolean categoryExistsByName = categoryRepository.existsByName(categoryDTO.getName());
            if (categoryExistsByName) {
                throw new CategoryException("Categoria já existe.", HttpStatus.CONFLICT);
            }

            this.categoryRepository.save(category);
            categoryDTO = mapper.map(category, CategoryDTO.class);

            return categoryDTO;
        } catch (Exception e) {
            throw new CategoryException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        CategoryDTO categoryDTOEncontrada = this.findById(id);
        try {

            categoryDTO.setId(id);
            Category category = mapper.map(categoryDTO, Category.class);
            this.categoryRepository.save(category);
            categoryDTO = mapper.map(category, CategoryDTO.class);
            return categoryDTO;
        } catch (Exception e) {
            throw new CategoryException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoryDTO findById(Long id) {
        try {
            Optional<Category> categoriaOptional = this.categoryRepository.findById(id);
            if (categoriaOptional.isPresent()) {
                CategoryDTO categoryDTO = mapper.map(categoriaOptional.get(), CategoryDTO.class);
                return categoryDTO;
            }

            throw new CategoryException("Categoria não encontrada.", HttpStatus.NOT_FOUND);

        } catch (CategoryException cex) {
            throw cex;
        } catch (Exception e) {
            throw new CategoryException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CategoryDTO> list() {
        try {
            List<Category> categories = this.categoryRepository.findAllByOrderByNameAsc();
            return this.mapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());
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
}