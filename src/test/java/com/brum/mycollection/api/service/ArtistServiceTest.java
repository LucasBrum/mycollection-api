package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.ArtistDTO;
import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.exception.CategoryException;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.service.impl.ArtistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistServiceImpl artistService;

    private Artist artist;

    private Category category;

    private ArtistDTO artistDTO;

    @BeforeEach
    private void setup() {
        category = Category.builder()
                .id(1L)
                .name("CD")
                .build();

        artist = Artist.builder()
                .id(1L)
                .category(category)
                .country("Brazil")
                .genre("Power Metal")
                .band("Angra")
                .title("Angels Cry")
                .releaseYear(1989)
                .build();

        artistDTO = ArtistDTO.builder()
                .id(1L)
                .category(category)
                .country("Brazil")
                .genre("Power Metal")
                .band("Angra")
                .title("Angels Cry")
                .releaseYear(1989)
                .build();
    }
    @Test
    @DisplayName("Test for Create Artist")
    public void testCreateArtist() {
        given(artistRepository.save(artist)).willReturn(artist);

        ArtistDTO savedArtist = artistService.create(artistDTO);

        assertThat(savedArtist).isNotNull();
    }

    @Test
    @DisplayName("Test for create with Internal Server Erro because Artist is null")
    public void testCreateArtistInternalServerError() {
        ArtistException artistException;

        artistException = assertThrows(ArtistException.class, () -> {
            this.artistService.create(null);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());

    }

    @Test
    @DisplayName("Test for delete Album")
    public void testDeleteCategory() {
        Long artistId = 1L;

        given(artistRepository.findById(artistId)).willReturn(Optional.of(artist));

        willDoNothing().given(artistRepository).deleteById(artistId);

        artistService.delete(artistId);

        verify(artistRepository, times(1)).deleteById(artistId);
    }

    @Test
    @DisplayName("Test for delete Album wich throws exception")
    public void testDeleteCategoryThrowException() {
        given(artistRepository.findById(anyLong())).willReturn(null);

        ArtistException artistException;

        artistException = assertThrows(ArtistException.class, () -> {
            this.artistService.delete(anyLong());
        });


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());
        verify(this.artistRepository, times(0)).deleteById(anyLong());

    }

}