package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.model.response.ArtistItemDetailsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findAllByOrderByNameAsc();

    Boolean existsArtistByName(String name);

    @Query("SELECT new com.brum.mycollection.api.model.response.ArtistItemDetailsResponse(a.id, a.name, a.country, i.title, i.genre, i.category.name, i.releaseYear) FROM Artist a, Item i")
    List<ArtistItemDetailsResponse> getArtistsAndItems();

}

