package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO criar(CategoryDTO categoryDTO);

    CategoryDTO atualizar(Long id, CategoryDTO categoryDTO);

    CategoryDTO buscarPeloId(Long id);

    List<CategoryDTO> listar();

    void delete(Long id);

}
