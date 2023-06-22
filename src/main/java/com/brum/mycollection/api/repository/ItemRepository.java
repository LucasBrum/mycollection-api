package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Boolean existsArtistByTitle(String title);
}
