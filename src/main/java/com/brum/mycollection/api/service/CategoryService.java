package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    CategoryDTO findById(Long id);

    List<CategoryDTO> list();

    void delete(Long id);

}
