package com.cineflow.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenRequest {

    @NotBlank(message = "{screen.name.required}")
    @Size(max=100, message = "{screen.name.length}")
    private String name;

    @Min(value = 100, message = "{screen.capacity.size}")
    private int capacity;

    @NotNull(message = "{screen.theatre.required}")
    private Long theatreId;
}
