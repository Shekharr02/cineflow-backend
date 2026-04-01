package com.cineflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreRequest {

    @NotBlank(message = "{theatre.name.blank}")
    @Size(max=100, message="{theatre.name.length}")
    private String name;

    @NotBlank(message = "{theatre.location.blank}")
    @Size(max=100, message = "{theatre.location.length}")
    private String location;
}
