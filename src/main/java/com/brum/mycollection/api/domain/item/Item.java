package com.brum.mycollection.api.domain.item;

import com.brum.mycollection.api.domain.artist.Artist;
import com.brum.mycollection.api.domain.category.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

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

    private byte[] coverImage;

    @ManyToOne
    @JoinColumn(name = "artistId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Artist artist;
}