package com.cineflow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotEmpty(message = "Seats are required")
    private List<Long> showSeatIds;
}
