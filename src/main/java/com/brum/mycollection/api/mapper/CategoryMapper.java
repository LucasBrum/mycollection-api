package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.domain.category.Category;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse(
                category.getId(),
                category.getName()
        );
        return categoryResponse;
    }

    public static Category toEntity(CategoryRequest categoryRequest) {
        Category category = Category.builder().name(categoryRequest.name()).build();

        return category;
    }

    public static List<CategoryResponse> toResponseList(List<Category> categoryList) {
        List<CategoryResponse> categoryResponses = categoryList.stream().map(CategoryMapper::toResponse).toList();

        return categoryResponses;
    }

}