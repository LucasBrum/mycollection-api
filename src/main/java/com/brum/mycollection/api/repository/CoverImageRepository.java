package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.CoverImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverImageRepository extends JpaRepository<CoverImage, Long> {
}
