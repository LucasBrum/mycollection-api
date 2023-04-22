package com.brum.mycollection.api.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryControllerTest {

	@InjectMocks
	private CategoryController categoryController;

	@ParameterizedTest
	public void listCategoriesTest() {

	}

}
