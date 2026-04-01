package com.cineflow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "{booking.showId.required}")
    private Long showId;

    @NotEmpty(message = "{booking.seats.min}")
    private List<Long> showSeatIds;
}
