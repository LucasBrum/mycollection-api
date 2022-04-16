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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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

}
