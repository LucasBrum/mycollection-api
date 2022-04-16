package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.CategoriaDTO;
import com.brum.mycollection.api.entity.Categoria;
import com.brum.mycollection.api.exception.CategoriaException;
import com.brum.mycollection.api.repository.CategoriaRepository;
import com.brum.mycollection.api.service.CategoriaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper mapper;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.mapper = new ModelMapper();
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaDTO criar(CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria  = this.mapper.map(categoriaDTO, Categoria.class);
            this.categoriaRepository.save(categoria);
            categoriaDTO = mapper.map(categoria, CategoriaDTO.class);

            return categoriaDTO;
        } catch (Exception e) {
            throw new CategoriaException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaDTOEncontrada = this.buscarPeloId(id);
        if (categoriaDTOEncontrada == null) {
            throw new CategoriaException("Categoria não encontrada.", HttpStatus.NO_CONTENT);
        }
        try {

            categoriaDTO.setId(id);
            Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
            this.categoriaRepository.save(categoria);
            categoriaDTO = mapper.map(categoria, CategoriaDTO.class);
            return categoriaDTO;
        } catch (Exception e) {
            throw new CategoriaException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoriaDTO buscarPeloId(Long id) {
        try {
            Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);
            if (categoriaOptional.isPresent()) {
                CategoriaDTO categoriaDTO = mapper.map(categoriaOptional.get(), CategoriaDTO.class);
                return categoriaDTO;
            }
        } catch (CategoriaException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CategoriaException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @Override
    public List<CategoriaDTO> listar() {
        try {
            List<Categoria> categorias = this.categoriaRepository.findAll();
            return this.mapper.map(categorias, new TypeToken<List<CategoriaDTO>>() {}.getType());
        } catch (Exception e) {
            throw new CategoriaException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            CategoriaDTO categoriaDTOEncontrada = this.buscarPeloId(id);
            if (categoriaDTOEncontrada == null) {
                throw new CategoriaException("Categoria não encontrada.", HttpStatus.NO_CONTENT);
            }
            this.categoriaRepository.deleteById(id);
        } catch (CategoriaException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CategoriaException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}