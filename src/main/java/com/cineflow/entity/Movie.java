package com.cineflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String genre;
    private int duration;
    private double rating;

    @ElementCollection
    private List<String> languages;
    private String censorRating;

    @Column(length = 1000)
    private String description;
    private LocalDate releaseDate;
    private String imageUrl;
}
