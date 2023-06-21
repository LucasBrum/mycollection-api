package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.entity.Item;
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
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Test if category name exists and return true")
    void existsArtistByTitleIsTrue() {
        Artist artist = createArtist("Mortification", "Australia");
        Category category = createCategory("CD");
        createItem("Break the Curse", 1990, "Death Metal", category, null, artist);

        Boolean existItemByTitle = itemRepository.existsArtistByTitle("Break the Curse");

        assertThat(existItemByTitle).isTrue();
    }

    @Test
    @DisplayName("Test if category name exists and return false")
    void existsArtistByTitleIsFalse() {
        Artist artist = createArtist("Mortification", "Australia");
        Category category = createCategory("CD");
        createItem("Break the Curse", 1990, "Death Metal", category, null, artist);

        Boolean existItemByTitle = itemRepository.existsArtistByTitle("Angels Cry");

        assertThat(existItemByTitle).isFalse();
    }

    private Artist createArtist(String band, String country) {
        Artist artist = testEntityManager.persist(new Artist(null, band, country));
        return artist;
    }

    private Category createCategory(String categoria) {
        Category category = testEntityManager.persist(new Category(null, categoria));
        return category;
    }

    private void createItem(String title, Integer releaseYear, String genre, Category category, byte[] coverImage, Artist artist) {
        testEntityManager.persist(new Item(null, title, releaseYear, genre, category, coverImage, artist));
    }
}