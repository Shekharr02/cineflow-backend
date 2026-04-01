package com.cineflow.service;

import com.cineflow.dto.BookingRequest;
import com.cineflow.dto.BookingResponse;
import com.cineflow.entity.*;
import com.cineflow.enums.BookingStatus;
import com.cineflow.enums.PaymentStatus;
import com.cineflow.enums.ShowSeatStatus;
import com.cineflow.exception.CineflowException;
import com.cineflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(timeout = 5)
    public BookingResponse bookTickets(BookingRequest request) {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CineflowException("user.not.found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new CineflowException("show.not.found"));

        Set<Long> uniqueSeats = new HashSet<>(request.getShowSeatIds());
        if (uniqueSeats.size() != request.getShowSeatIds().size()) {
            throw new CineflowException("seat.duplicate");
        }
        List<ShowSeat> seats = showSeatRepository.findAllByIdWithLock(request.getShowSeatIds());

        if (seats.size() != request.getShowSeatIds().size()) {
            throw new CineflowException("seat.invalid");
        }

        for (ShowSeat seat : seats) {
            if (!seat.getShow().getId().equals(show.getId())) {
                throw new CineflowException("seat.not.belongs.to.show");
            }
            if (seat.getStatus() != ShowSeatStatus.AVAILABLE) {
                throw new CineflowException("seat.not.available");
            }
            seat.setStatus(ShowSeatStatus.HELD);
            seat.setLockedAt(LocalDateTime.now());
            seat.setLockedBy(user);
        }
        showSeatRepository.saveAll(seats);
        double total = seats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        booking.setTotalAmount(total);
        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.PENDING);
        List<BookingSeat> bookingSeats = new ArrayList<>();
        for(ShowSeat seat : seats){
            BookingSeat bs = new BookingSeat();
            bs.setBooking(booking);
            bs.setShowSeat(seat);
            bs.setPrice(seat.getPrice());
            bookingSeats.add(bs);
        }
        booking.setBookingSeats(bookingSeats);
        Booking saved = bookingRepository.save(booking);
        List<String> seatNumbers = seats.stream().map(
                s -> s.getSeat().getSeatNumber()).toList();

        return new BookingResponse(saved.getId(),
                show.getMovie().getName(),
                show.getScreen().getTheatre().getName(),
                show.getShowTime(),
                seatNumbers,
                total,
                BookingStatus.PENDING
        );
    }

    @Override
    public List<BookingResponse> getUserBookings(Long userId){
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream().map(b-> new BookingResponse(
                b.getId(),
                b.getShow().getMovie().getName(),
                b.getShow().getScreen().getTheatre().getName(),
                b.getShow().getShowTime(),
                b.getBookingSeats().stream().map(bs->
                        bs.getShowSeat().getSeat().getSeatNumber()).toList(),
                b.getTotalAmount(),
                b.getStatus()
        )).toList();
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new CineflowException("booking.not.found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new CineflowException("user.not.found"));

        if(!booking.getUser().getId().equals(currentUser.getId())) {
            throw new CineflowException("unauthorized.action");
        }
        if(booking.getStatus() == BookingStatus.CANCELLED){
            throw new CineflowException("booking.already.canceled");
        }

        List<ShowSeat> seats = booking.getBookingSeats().stream()
                .map(BookingSeat::getShowSeat).toList();

        for(ShowSeat seat : seats){
            seat.setStatus(ShowSeatStatus.AVAILABLE);
            seat.setLockedAt(null);
            seat.setLockedBy(null);
        }
        showSeatRepository.saveAll(seats);

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setPaymentStatus(PaymentStatus.FAILED);
        bookingRepository.save(booking);
    }
}
