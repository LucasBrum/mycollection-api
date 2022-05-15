package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.CategoryDTO;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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
    @DisplayName("Test for create category")
    public void testCreateCategory() {
        given(categoryRepository.save(category)).willReturn(category);

        CategoryDTO savedCategoria = categoriaService.create(categoryDTO);

        assertThat(savedCategoria).isNotNull();

    }

    @Test
    @DisplayName("Test for create category wich throw exception")
    public void testCreateCategoryAllThrowException() {
        given(this.categoryRepository.existsByName(anyString())).willReturn(Boolean.TRUE);

        CategoryException categoryException;

        categoryException = assertThrows(CategoryException.class, () -> {
            this.categoriaService.create(categoryDTO);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, categoryException.getHttpStatus());
        verify(this.categoryRepository, times(1)).existsByName(anyString());

    }

    @Test
    @DisplayName("Test for delete category")
    public void testDeleteCategory() {
        Long categoryId = 1L;

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        willDoNothing().given(categoryRepository).deleteById(categoryId);

        categoriaService.delete(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Test for delete category wich throws exception")
    public void testDeleteCategoryThrowException() {
        given(categoryRepository.findById(anyLong())).willReturn(null);

        CategoryException categoryException;

        categoryException = assertThrows(CategoryException.class, () -> {
            this.categoriaService.delete(anyLong());
        });


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, categoryException.getHttpStatus());
        verify(this.categoryRepository, times(0)).deleteById(anyLong());

    }

    @Test
    @DisplayName("Test for update category")
    public void testUpdateCategory() {
        Long categoryId = 1L;

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));
        lenient().when(categoryRepository.save(category)).thenReturn(category);
        categoryDTO.setName("CD Alterado");

        CategoryDTO updatedCategory = categoriaService.update(categoryId, categoryDTO);

        assertThat(updatedCategory.getName()).isEqualTo("CD Alterado");
    }

    @Test
    @DisplayName("Test for update category not found wich throws exception")
    public void testUpdateCategoryNotFoundThrowException() {
        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());
        lenient().when(categoryRepository.save(category)).thenReturn(category);
        categoryDTO.setName("CD Alterado");

        CategoryException categoryException;

        categoryException = assertThrows(CategoryException.class, () -> {
            this.categoriaService.update(anyLong(), categoryDTO);
        });

        assertEquals(HttpStatus.NO_CONTENT, categoryException.getHttpStatus());
        verify(this.categoryRepository, times(0)).save(category);

    }

    @Test
    @DisplayName("Test for update category wich throws exception")
    public void testUpdateCategoryThrowException() {
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));
        lenient().when(categoryRepository.save(null)).thenReturn(null);

        CategoryException categoryException;

        categoryException = assertThrows(CategoryException.class, () -> {
            this.categoriaService.update(anyLong(), null);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, categoryException.getHttpStatus());
        verify(this.categoryRepository, times(0)).save(category);

    }

    @Test
    @DisplayName("Test for list all categories")
    public void testListCategories() {
        Long categoryId = 1L;

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        given(categoryRepository.findAllByOrderByNameAsc()).willReturn(categoryList);

        List<CategoryDTO> categoryDTOList = categoriaService.list();

        assertThat(categoryDTOList.size()).isEqualTo(1);

    }

    @Test
    @DisplayName("Test for list all categories wich throw exception")
    public void testListCategoriesThrowException() {
        given(categoryRepository.findAllByOrderByNameAsc()).willReturn(null);

        CategoryException categoryException;

        categoryException = assertThrows(CategoryException.class, () -> {
            this.categoriaService.list();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, categoryException.getHttpStatus());
        verify(this.categoryRepository, times(1)).findAllByOrderByNameAsc();

    }

    @Test
    @DisplayName("Test find Category by Id")
    public void testFindCategoryById() {
        Long categoryId = 1L;

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        CategoryDTO categoryDTO = categoriaService.findById(categoryId);

        assertThat(categoryDTO).isNotNull();

    }

    @Test
    @DisplayName("Test find Category by Id Returning Null")
    public void testFindCategoryByIdReturningNull() {
        Long categoryId = 1L;

        List<Category> categoryList = new ArrayList<>();


        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        CategoryDTO categoryDTO = categoriaService.findById(categoryId);

        assertThat(categoryDTO).isNull();

    }

    @Test
    @DisplayName("Test find category by id throw exception")
    public void testFindCategoryByIdAllThrowException() {
        Long categoryId = 1L;
        when(this.categoryRepository.findById(categoryId)).thenThrow(IllegalStateException.class);

        CategoryException categoryException;

        categoryException = assertThrows(CategoryException.class, () -> {
            this.categoriaService.findById(categoryId);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, categoryException.getHttpStatus());
        verify(this.categoryRepository, times(1)).findById(categoryId);
    }


}
