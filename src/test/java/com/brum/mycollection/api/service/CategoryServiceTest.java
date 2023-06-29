package com.brum.mycollection.api.service;

import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.mapper.CategoryMapper;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.repository.CategoryRepository;
import com.brum.mycollection.api.service.impl.CategoryServiceImpl;
import com.brum.mycollection.api.util.Messages;
import com.brum.mycollection.api.validations.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private List<Validator<CategoryRequest>> validators;

    @BeforeEach
    public void setup() {
        //categoryRepository = Mockito.mock(CategoryRepository.class); //Can be replaced by @Mock
        //categoryService = new CategoryServiceImpl(categoryRepository); //Can be replaced by @InjectMocks
    }

    @Test
    @DisplayName("Test the Create Category.")
    public void testCreateCategorySuccess() {

        CategoryRequest categoryRequest = new CategoryRequest("CD");

        Category category = CategoryMapper.toEntity(categoryRequest);

        BDDMockito.given(categoryRepository.save(category)).willReturn(category);

        CategoryResponse savedCategory = categoryService.create(categoryRequest);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.name()).isEqualTo("CD");
    }

    @Test
    @DisplayName("Test the Update Category.")
    public void testUpdateCategorySuccess() {

        CategoryRequest categoryRequest = new CategoryRequest("DVD");

        Optional<Category> category = getCategoryOptional();

        BDDMockito.given(categoryRepository.findById(1L)).willReturn(category);

        CategoryResponse savedCategory = categoryService.update(1L, categoryRequest);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.name()).isEqualTo("DVD");
    }

    @Test
    @DisplayName("Test Find Category by Id")
    public void testFindCategoryById() {
        Optional<Category> categoryOptinonal = getCategoryOptional();

        BDDMockito.given(categoryRepository.findById(1L)).willReturn(categoryOptinonal);

        CategoryResponse categoryResponse = this.categoryService.findById(1L);

        assertThat(categoryResponse).isNotNull();

    }

    @Test
    @DisplayName("Test Find Category by Id. Category Not Found")
    public void testFindCategoryByIdThrowsException() {
        BDDMockito.given(categoryRepository.findById(1L)).willReturn(Optional.empty());

        CategoryException categoryException = assertThrows(CategoryException.class, () -> {
            categoryService.findById(1L);
        });

        assertThat(categoryException.getMessage()).isEqualTo(Messages.CATEGORY_NOT_FOUND);

    }

    @Test
    @DisplayName("Test Find Category by Id. Category Not Found")
    public void testCategoryResponseList() {

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(getCategoryOptional().get());

        BDDMockito.given(categoryRepository.findAllByOrderByNameAsc()).willReturn(categoryList);

        List<CategoryResponse> categoryResponsesList = this.categoryService.list();

        assertThat(categoryResponsesList.size()).isEqualTo(1);
        assertThat(categoryResponsesList.get(0).name()).isEqualTo("CD");
    }

    @Test
    @DisplayName("Test Delete Category by Id.")
    public void testDeleteCategory() {
        Optional<Category> categoryOptinonal = getCategoryOptional();
         BDDMockito.given(categoryRepository.findById(1L)).willReturn(categoryOptinonal);

        this.categoryService.delete(1L);


    }

    private Optional<Category> getCategoryOptional() {
        Optional<Category> category = Optional.of(Category.builder()
                .id(1L)
                .name("CD")
                .build());
        return category;
    }

}