package com.cineflow.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieRequest {
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
