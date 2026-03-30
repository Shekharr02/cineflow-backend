package com.cineflow.service;

import com.cineflow.dto.BookingRequest;
import com.cineflow.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse bookTickets(BookingRequest request);

    List<BookingResponse> getUserBookings(Long userId);

    void cancelBooking(Long bookingId);

    BookingResponse confirmBooking(List<Long> showSeatIds);
}
