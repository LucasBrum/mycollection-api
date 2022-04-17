package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.CategoryDTO;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoriaService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {

        category = Category.builder()
                .id(1L)
                .nome("Cd")
                .build();

        categoryDTO = CategoryDTO.builder()
                .id(1L)
                .nome("Cd")
                .build();

    }

    @Test
    @DisplayName("Junit Test for create Category method")
    public void givenCategoryObject_whenSaveCategory_thenReturnCategoryObject() {
        given(categoryRepository.save(category)).willReturn(category);

        CategoryDTO savedCategoria = categoriaService.criar(categoryDTO);

        assertThat(savedCategoria).isNotNull();

    }

    @Test
    @DisplayName("JUnit test for delete Category method")
    public void givenCategoryId_whenDeleteCategory_thenNothing() {
        Long categoryId = 1L;

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));
        willDoNothing().given(categoryRepository).deleteById(categoryId);

        categoriaService.delete(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

}
