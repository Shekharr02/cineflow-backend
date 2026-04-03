package com.cineflow.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {

    @DecimalMin(value = "1.0", message = "{rating.range}")
    @DecimalMax(value = "5.0", message = "{rating.range}")
    private double ratingValue;

    @Size(max = 1000, message = "{rating.review.length}")
    private String reviewMessage;
}
