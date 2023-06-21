package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    public static final String CD = "CD";
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

        Boolean existCategory= categoryRepository.existsCategoryByName(CD);
        assertThat(existCategory).isFalse();
    }

    private void createCategory(String categoria) {
        testEntityManager.persist(new Category(null, categoria));
    }
}