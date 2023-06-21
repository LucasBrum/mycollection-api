package com.brum.mycollection.api.service;

import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    CategoryResponse findById(Long id);

    List<CategoryResponse> list();

    void delete(Long id);

}
