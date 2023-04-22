package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.CategoryDTO;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response<CategoryResponse>> create(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = this.categoryService.create(categoryRequest);
        Response<CategoryResponse> response = new Response<>();
        response.setData(categoryResponse);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoryResponse>>> list() {
        List<CategoryResponse> categoryResponseList = this.categoryService.list();

        Response<List<CategoryResponse>> response = new Response<>();
        response.setData(categoryResponseList);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryResponse>> findById(@PathVariable Long id) {
        CategoryResponse categoryResponse = this.categoryService.findById(id);

        Response<CategoryResponse> response = new Response<>();
        response.setData(categoryResponse);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoryResponse>> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponseUpdated = categoryService.update(id, categoryRequest);

        Response<CategoryResponse> response = new Response<>();
        response.setData(categoryResponseUpdated);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {
        this.categoryService.delete(id);

        Response<Boolean> response = new Response<>();
        response.setData(Boolean.TRUE);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}