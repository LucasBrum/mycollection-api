package com.brum.mycollection.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "labels")
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonInclude(Include.NON_NULL)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @JsonInclude(Include.NON_EMPTY)
    @Column(nullable = false)
    private String name;

    @Column(name = "discogs_id")
    private Integer discogsId;

}
