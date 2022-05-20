package com.brum.mycollection.api.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void testCreateNewCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Dvd");

        Assertions.assertNotNull(category);
        Assertions.assertEquals(1L, category.getId());
        Assertions.assertEquals("Dvd", category.getName());
    }

    @Test
    public void testCreateNewCategoryNoArgsConstructor() {
        Assertions.assertNotNull(new Category());
    }

    @Test
    public void testCreateNewCategoryAllArgsConstructor() {
        Category category = new Category(1L, "Dvd");

        Assertions.assertNotNull(category);
        Assertions.assertEquals(1L, category.getId());
        Assertions.assertEquals("Dvd", category.getName());
    }

    @Test
    public void testCreateNewCategoryBuilder() {
        Category category = Category.builder().id(1L).name("Dvd").build();
        Assertions.assertNotNull(category);
        Assertions.assertEquals(1L, category.getId());
        Assertions.assertEquals("Dvd", category.getName());
    }
}