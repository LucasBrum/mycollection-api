package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.CategoriaDTO;
import com.brum.mycollection.api.entity.Categoria;

import java.util.List;

public interface CategoriaService {

    CategoriaDTO criar(CategoriaDTO categoriaDTO);

    CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTO);

    CategoriaDTO buscarPeloId(Long id);

    List<CategoriaDTO> listar();

    void delete(Long id);

}
