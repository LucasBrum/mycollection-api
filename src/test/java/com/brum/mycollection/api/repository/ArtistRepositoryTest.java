package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.domain.artist.Artist;
import com.brum.mycollection.api.domain.category.Category;
import com.brum.mycollection.api.domain.item.Item;
import com.brum.mycollection.api.model.response.ArtistItemDetailsResponse;
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
public class ArtistRepositoryTest {

    public static final String CD = "CD";
    public static final String MORTIFICATION = "Mortification";
    public static final String AUSTRALIA = "Australia";
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Test Find All Artists sorted by Name")
    void findAllByOrderByNameAsc() {

        createArtist("Mortification", "Australia");
        createArtist("Angra", "Brazil");
        createArtist("Borealis", "Canada");

        List<Artist> artistList = artistRepository.findAllByOrderByNameAsc();

        assertThat(artistList.size()).isEqualTo(3);
        assertThat(artistList.get(0).getName()).isEqualTo("Angra");
        assertThat(artistList.get(1).getName()).isEqualTo("Borealis");
    }

    @Test
    @DisplayName("Test if artist exist by name")
    void existsArtistByNameIsTrue() {
        createArtist("Mortification", "Australia");

        Boolean existsArtist = artistRepository.existsArtistByName("Mortification");

        assertThat(existsArtist).isTrue();
    }

    @Test
    @DisplayName("Test if artist exist by name return false")
    void existsArtistByNameIsFalse() {
        createArtist(MORTIFICATION, AUSTRALIA);

        Boolean existsArtist = artistRepository.existsArtistByName("Angra");

        assertThat(existsArtist).isFalse();
    }

    @Test
    @DisplayName("Test Get Items details from Artist")
    void getArtistsItemsDetails() {
        Artist artist = createArtist(MORTIFICATION, AUSTRALIA);
        Category category = createCategory(CD);
        createItem("Break the Curse", 1990, "Death Metal", category, null, artist);

        List<ArtistItemDetailsResponse> artistItemDetailsResponseList = artistRepository.getArtistsItemsDetails();

        assertThat(artistItemDetailsResponseList.size()).isEqualTo(1);
        assertThat(artistItemDetailsResponseList.get(0).title()).isEqualTo("Break the Curse");
    }

    private Artist createArtist(String band, String country) {
        Artist artist = testEntityManager.persist(new Artist(null, band, country));
        return artist;
    }

    private Category createCategory(String name) {
        Category category = testEntityManager.persist(new Category(null, name));
        return category;
    }

    private void createItem(String title, Integer releaseYear, String genre, Category category, byte[] coverImage, Artist artist) {
        testEntityManager.persist(new Item(null, title, releaseYear, genre, category, coverImage, artist));
    }
}