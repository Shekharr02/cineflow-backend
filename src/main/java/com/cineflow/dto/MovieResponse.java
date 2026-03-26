package com.cineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private Long id;
    private String name;
    private String genre;
    private int duration;
    private double rating;
    private List<String> languages;
    private String censorRating;
    private String description;
    private LocalDate releaseDate;
    private String imageUrl;
}
