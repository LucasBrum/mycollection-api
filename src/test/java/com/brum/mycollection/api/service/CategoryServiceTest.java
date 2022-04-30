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
import static org.mockito.Mockito.*;

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
                .name("CD")
                .build();

        categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("CD")
                .build();

    }

    @Test
    @DisplayName("Test for Create Category")
    public void testCreateCategory() {
        given(categoryRepository.save(category)).willReturn(category);

        CategoryDTO savedCategoria = categoriaService.create(categoryDTO);

        assertThat(savedCategoria).isNotNull();

    }

    @Test
    @DisplayName("Test for Delete Category")
    public void testDeleteCategory() {
        Long categoryId = 1L;

        // given
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        // when
        willDoNothing().given(categoryRepository).deleteById(categoryId);

        //then
        categoriaService.delete(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testUpdateCategory() {
        Long categoryId = 1L;

        // given
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));
        lenient().when(categoryRepository.save(category)).thenReturn(category);
        categoryDTO.setName("CD Alterado");

        // when
        CategoryDTO updatedCategory = categoriaService.update(categoryId, categoryDTO);

        // then
        assertThat(updatedCategory.getName()).isEqualTo("CD Alterado");
    }

}
