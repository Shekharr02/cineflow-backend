package com.cineflow.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenRequest {

    @NotBlank(message = "Screen name is required")
    private String name;

    @Min(value = 100, message = "Capacity must be at least 100")
    private int capacity;

    @NotNull(message = "Theatre ID is required")
    private Long theatreId;
}
