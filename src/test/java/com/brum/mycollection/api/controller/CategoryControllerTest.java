package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.fixture.CategoryFixture;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryControllerTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private CategoryService categoryService;

	private final ModelMapper mapper;

	public CategoryControllerTest() {
		this.mapper = new ModelMapper();
	}

	@ParameterizedTest
	@ValueSource(longs = {1L})
	public void listCategoriesTest(long id) {
		var category = CategoryFixture.getCategoryEntity();

		var categoryResponse = this.mapper.map(category, CategoryResponse.class);

		var categoryListResponse = List.of(categoryResponse);

		given(categoryService.list()).willReturn(categoryListResponse);

		var categoryListResponseReturned = categoryController.list();

		Assertions.assertEquals(categoryListResponse, categoryController.list());
	}

}
