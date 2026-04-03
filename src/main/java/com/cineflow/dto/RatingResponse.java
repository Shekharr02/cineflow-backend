package com.cineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private Long movieId;
    private String movieName;
    private double userRating;
    private String reviewMessage;
    private double newAverageRating;
}
