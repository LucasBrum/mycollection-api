package com.brum.mycollection.api.service;

import com.brum.mycollection.api.domain.category.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.mapper.CategoryMapper;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup() {
        //categoryRepository = Mockito.mock(CategoryRepository.class); //Can be replaced by @Mock
        //categoryService = new CategoryServiceImpl(categoryRepository); //Can be replaced by @InjectMocks
    }

    @Test
    @DisplayName("Test the Create Category method.")
    public void testCreateCategorySuccess() {

        CategoryRequest categoryRequest = new CategoryRequest("CD");

        Category category = CategoryMapper.toEntity(categoryRequest);

        BDDMockito.given(categoryRepository.existsCategoryByName(categoryRequest.name())).willReturn(Boolean.FALSE);
        BDDMockito.given(categoryRepository.save(category)).willReturn(category);

        CategoryResponse savedCategory = categoryService.create(categoryRequest);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.name()).isEqualTo("CD");
    }

    @Test
    @DisplayName("Test try to create a new Category with name already exists witch throws exception.")
    public void testCreateCategoryWithNameAlreadyExistsThrowsException() {

        CategoryRequest categoryRequest = new CategoryRequest("CD");

        Category category = CategoryMapper.toEntity(categoryRequest);

        BDDMockito.given(categoryRepository.existsCategoryByName(categoryRequest.name())).willReturn(Boolean.TRUE);

        CategoryException categoryException = assertThrows(CategoryException.class, () -> {
            categoryService.create(categoryRequest);
        });

        verify(categoryRepository, never()).save(any(Category.class));
        assertThat(categoryException.getMessage()).isEqualTo("Category already exists.");
    }
}