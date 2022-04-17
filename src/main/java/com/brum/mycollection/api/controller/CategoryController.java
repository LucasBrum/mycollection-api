package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.CategoryDTO;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response<CategoryDTO>> create(@RequestBody CategoryDTO categoryDTO) {
            CategoryDTO categoryCreated = this.categoryService.create(categoryDTO);
        Response<CategoryDTO> response = new Response<>();
        response.setData(categoryCreated);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoryDTO>>> list() {
        List<CategoryDTO> categoryDTOList = this.categoryService.list();

        Response<List<CategoryDTO>> response = new Response<>();
        response.setData(categoryDTOList);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDTO>> findById(@PathVariable Long id) {
        CategoryDTO categoryDTO = this.categoryService.findById(id);

        Response<CategoryDTO> response = new Response<>();
        response.setData(categoryDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoryDTO>> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryUpdated = categoryService.update(id, categoryDTO);

        Response<CategoryDTO> response = new Response<>();
        response.setData(categoryUpdated);
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