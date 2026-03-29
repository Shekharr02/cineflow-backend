package com.cineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;
    private String movieName;
    private String theatreName;
    private LocalDateTime showTime;
    private List<String> seats;
    private double totalAmount;
}
