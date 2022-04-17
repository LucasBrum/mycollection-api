package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.CategoriaDTO;
import com.brum.mycollection.api.entity.Categoria;
import com.brum.mycollection.api.repository.CategoriaRepository;
import com.brum.mycollection.api.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;

    @BeforeEach
    public void setup() {

        categoria = Categoria.builder()
                .id(1L)
                .nome("Cd")
                .build();

        categoriaDTO = CategoriaDTO.builder()
                .id(1L)
                .nome("Cd")
                .build();

    }

    @Test
    @DisplayName("Junit Test for create Category method")
    public void givenCategoryObject_whenSaveCategory_thenReturnCategoryObject() {
        given(categoriaRepository.save(categoria)).willReturn(categoria);

        CategoriaDTO savedCategoria = categoriaService.criar(categoriaDTO);

        assertThat(savedCategoria).isNotNull();

    }

    @Test
    @DisplayName("JUnit test for delete Category method")
    public void givenCategoryId_whenDeleteCategory_thenNothing() {
        Long categoryId = 1L;

        given(categoriaRepository.findById(categoryId)).willReturn(Optional.of(categoria));
        willDoNothing().given(categoriaRepository).deleteById(categoryId);

        categoriaService.delete(categoryId);

        verify(categoriaRepository, times(1)).deleteById(categoryId);
    }

}
