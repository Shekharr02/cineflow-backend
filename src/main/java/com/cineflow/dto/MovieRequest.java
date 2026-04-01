package com.cineflow.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieRequest {

    @NotBlank(message = "{movie.name.blank}")
    @Size(min = 2, max = 100, message = "{movie.name.length}")
    private String name;

    @NotBlank(message = "{movie.genre.blank}")
    @Size(min = 3, max = 50, message = "{movie.genre.length}")
    private String genre;

    private int duration;

    @DecimalMin(value = "0.0", message = "{movie.rating.range}")
    @DecimalMax(value = "5.0", message = "{movie.rating.range}")
    private double rating;

    @NotBlank(message = "{movie.language.blank}")
    private List<String> languages;

    @Size(max = 10, message = "{movie.censor.length}")
    private String censorRating;

    @Size(max = 2000, message = "{movie.description.length}")
    private String description;
    private LocalDate releaseDate;

    @NotBlank(message = "{movie.image.blank}")
    @Size(max = 255, message = "{movie.image.length}")
    private String imageUrl;
}
