package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findAllByOrderByNameAsc();

    Boolean existsArtistByName(String name);
}
