package com.brum.mycollection.api.service;

import com.brum.mycollection.api.entity.Artist;
import com.brum.mycollection.api.entity.Category;
import com.brum.mycollection.api.repository.ArtistRepository;
import com.brum.mycollection.api.service.impl.ArtistServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistServiceImpl artistService;

    private Artist artist;

    private Category category;



}