package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.ArtistDTO;
import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.exception.ArtistException;
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

import java.io.IOException;
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
//    @Test
//    @DisplayName("Test for Create Artist")
//    public void testCreateArtist() throws IOException {
//        given(artistRepository.save(artist)).willReturn(artist);
//
//        ArtistDTO savedArtist = artistService.create(artistDTO, null);
//
//        assertThat(savedArtist).isNotNull();
//    }

//    @Test
//    @DisplayName("Test for create with Internal Server Erro because Artist is null")
//    public void testCreateArtistInternalServerError() {
//        ArtistException artistException;
//
//        artistException = assertThrows(ArtistException.class, () -> {
//            this.artistService.create(null, null);
//        });
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());
//
//    }
//
//    @Test
//    @DisplayName("Test for delete Album")
//    public void testDeleteCategory() {
//        Long artistId = 1L;
//
//        given(artistRepository.findById(artistId)).willReturn(Optional.of(artist));
//
//        willDoNothing().given(artistRepository).deleteById(artistId);
//
//        artistService.delete(artistId);
//
//        verify(artistRepository, times(1)).deleteById(artistId);
//    }
//
//    @Test
//    @DisplayName("Test for delete Album wich throws exception")
//    public void testDeleteCategoryThrowException() {
//        given(artistRepository.findById(anyLong())).willReturn(null);
//
//        ArtistException artistException;
//
//        artistException = assertThrows(ArtistException.class, () -> {
//            this.artistService.delete(anyLong());
//        });
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());
//        verify(this.artistRepository, times(0)).deleteById(anyLong());
//
//    }
//
//    @Test
//    @DisplayName("Test for update album")
//    public void testUpdateArtist() {
//        Long artistId = 1L;
//
//        given(artistRepository.findById(artistId)).willReturn(Optional.of(artist));
//        lenient().when(artistRepository.save(artist)).thenReturn(artist);
//        artistDTO.setBand("Angra alterado");
//
//        ArtistDTO updatedArtist = artistService.update(artistId, artistDTO);
//
//        assertThat(updatedArtist.getBand()).isEqualTo("Angra alterado");
//    }
//
//    @Test
//    @DisplayName("Test for update album wich throws exception")
//    public void testUpdateAlbumThrowException() {
//        given(artistRepository.findById(anyLong())).willReturn(Optional.of(artist));
//        lenient().when(artistRepository.save(null)).thenReturn(null);
//
//        ArtistException artistException;
//
//        artistException = assertThrows(ArtistException.class, () -> {
//            this.artistService.update(anyLong(), null);
//        });
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());
//        verify(this.artistRepository, times(0)).save(artist);
//
//    }
//
//    @Test
//    @DisplayName("Test find Artist by Id")
//    public void testFindAlbumById() {
//        Long artistId = 1L;
//
//        List<Artist> artistList = new ArrayList<>();
//        artistList.add(artist);
//
//        given(artistRepository.findById(artistId)).willReturn(Optional.of(artist));
//
//        ArtistDTO artistDTO = artistService.findById(artistId);
//
//        assertThat(artistDTO).isNotNull();
//
//    }
//
//    @Test
//    @DisplayName("Test find Artist by id throw exception")
//    public void testFindArtistByIdAllThrowException() {
//        Long artistId = 1L;
//        when(this.artistRepository.findById(artistId)).thenThrow(IllegalStateException.class);
//
//        ArtistException artistException;
//
//        artistException = assertThrows(ArtistException.class, () -> {
//            this.artistService.findById(artistId);
//        });
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());
//        verify(this.artistRepository, times(1)).findById(artistId);
//    }
//
//    @Test
//    @DisplayName("Test find artist by id throw Artist Exception")
//    public void testFindCategoryByIdAllThrowCategoryException() {
//        Long artistId = 1L;
//        when(this.artistRepository.findById(artistId)).thenReturn(Optional.empty());
//
//        ArtistException artistException;
//
//        artistException = assertThrows(ArtistException.class, () -> {
//            this.artistService.findById(artistId);
//        });
//
//        assertEquals(HttpStatus.NOT_FOUND, artistException.getHttpStatus());
//        verify(this.artistRepository, times(1)).findById(artistId);
//    }
//
//    @Test
//    @DisplayName("Test for list all artists")
//    public void testListCategories() {
//        Long artistId = 1L;
//
//        List<Artist> artists = new ArrayList<>();
//        artists.add(artist);
//
//        List<ArtistDTO> artistDTOList = artistService.list();
//
//        assertThat(artistDTOList.size()).isEqualTo(0);
//
//    }
//
//    @Test
//    @DisplayName("Test for list all artists wich throw exception")
//    public void testListArtistsThrowException() {
//       given(artistRepository.findAllByOrderByBandAsc()).willReturn(null);
//
//        ArtistException artistException;
//
//        artistException = assertThrows(ArtistException.class, () -> {
//            this.artistService.list();
//        });
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, artistException.getHttpStatus());
//        verify(this.artistRepository, times(1)).findAllByOrderByBandAsc();
//
//    }

}