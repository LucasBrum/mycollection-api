package com.brum.mycollection.api.model.response;

import com.brum.mycollection.api.entity.Category;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemResponse {

    private String title;

    private Integer releaseYear;

    private String genre;

    private Category category;

    private byte[] coverImage;
}