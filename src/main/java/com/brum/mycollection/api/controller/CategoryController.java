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
    public ResponseEntity<Response<CategoryDTO>> criar(@RequestBody CategoryDTO categoryDTO) {
            CategoryDTO categoriaCriada = this.categoryService.criar(categoryDTO);
        Response<CategoryDTO> response = new Response<>();
        response.setData(categoriaCriada);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoryDTO>>> listar() {
        List<CategoryDTO> categoriasDTO = this.categoryService.listar();

        Response<List<CategoryDTO>> response = new Response<>();
        response.setData(categoriasDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDTO>> buscarPeloId(@PathVariable Long id) {
        CategoryDTO categoryDTO = this.categoryService.buscarPeloId(id);

        Response<CategoryDTO> response = new Response<>();
        response.setData(categoryDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoryDTO>> atualizar(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoriaAtualizada = categoryService.atualizar(id, categoryDTO);

        Response<CategoryDTO> response = new Response<>();
        response.setData(categoriaAtualizada);
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