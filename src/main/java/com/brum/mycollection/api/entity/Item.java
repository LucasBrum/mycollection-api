package com.brum.mycollection.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item implements Serializable {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String title;

    private Integer releaseYear;

    private String genre;

    @ManyToOne
    private Category category;

    @Column(name = "cover_image_path")
    private String coverImagePath;

    @ManyToOne
    @JoinColumn(name = "artistId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Artist artist;

    // Novos campos para integração com Discogs
    @ManyToOne
    @JoinColumn(name = "label_id")
    private Label label;

    @Column(name = "discogs_release_id")
    private Integer discogsReleaseId;

    @Column(name = "discogs_master_id")
    private Integer discogsMasterId;

    @Column(name = "discogs_image_url")
    private String discogsImageUrl;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Track> tracks = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Credit> credits = new ArrayList<>();

}