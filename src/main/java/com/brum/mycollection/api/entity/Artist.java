package com.brum.mycollection.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artists")
public class Artist implements Serializable {

    private static final long serialVersionUID = -5115707874529054925L;

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String name;

    private String country;



}