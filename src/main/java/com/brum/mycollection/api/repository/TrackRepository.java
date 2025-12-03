package com.brum.mycollection.api.repository;

import com.brum.mycollection.api.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findByItemIdOrderByTrackOrder(Long itemId);

    void deleteByItemId(Long itemId);

}
