package com.cineflow.controller;

import com.cineflow.dto.BookingRequest;
import com.cineflow.dto.BookingResponse;
import com.cineflow.entity.User;
import com.cineflow.repository.UserRepository;
import com.cineflow.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest request){
        return ResponseEntity.ok(bookingService.bookTickets(request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return ResponseEntity.ok(bookingService.getUserBookings(user.getId()));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled");
    }
}
