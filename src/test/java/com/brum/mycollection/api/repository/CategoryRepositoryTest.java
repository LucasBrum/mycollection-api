package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    public static final String CD = "CD";
    public static final String DVD = "DVD";
    public static final String BOOK = "Book";
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Test if category name exists and return true")
    void existsCategoryByNameIsTrue() {

        createCategory(CD);

        Boolean existCategory = categoryRepository.existsCategoryByName(CD);
        assertThat(existCategory).isTrue();
    }

    @Test
    @DisplayName("Test if category name exists and return false")
    void existsCategoryByNameIsFalse() {

        Boolean existCategory = categoryRepository.existsCategoryByName(CD);
        assertThat(existCategory).isFalse();
    }

    @Test
    @DisplayName("Test Find All categories sorted by name ASC")
    void findAllByOrderByNameAsc() {
        createCategory(CD);
        createCategory(DVD);
        createCategory(BOOK);

        List<Category> categoryList = categoryRepository.findAllByOrderByNameAsc();

        assertThat(categoryList.size()).isEqualTo(3);
        assertThat(categoryList.get(0).getName()).isEqualTo(BOOK);
    }

    private void createCategory(String categoria) {
        testEntityManager.persist(new Category(null, categoria));
    }
}