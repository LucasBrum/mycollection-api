package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.CategoriaDTO;
import com.brum.mycollection.api.entity.Categoria;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Response<CategoriaDTO>> criar(@RequestBody CategoriaDTO categoriaDTO) {
        categoriaDTO = this.categoriaService.criar(categoriaDTO);
        Response<CategoriaDTO> response = new Response<>();
        response.setData(categoriaDTO);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoriaDTO>>> listar() {
        List<CategoriaDTO> categoriasDTO = this.categoriaService.listar();

        Response<List<CategoriaDTO>> response = new Response<>();
        response.setData(categoriasDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoriaDTO>> buscarPeloId(@PathVariable Long id) {
        CategoriaDTO categoriaDTO = this.categoriaService.buscarPeloId(id);

        Response<CategoriaDTO> response = new Response<>();
        response.setData(categoriaDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoriaDTO>> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        categoriaDTO = categoriaService.atualizar(id, categoriaDTO);

        Response<CategoriaDTO> response = new Response<>();
        response.setData(categoriaDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {
        this.categoriaService.delete(id);

        Response<Boolean> response = new Response<>();
        response.setData(Boolean.TRUE);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}