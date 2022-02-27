package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.dto.CategoriaDTO;
import com.brum.mycollection.api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
