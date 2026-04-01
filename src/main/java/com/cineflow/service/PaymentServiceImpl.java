package com.cineflow.service;

import com.cineflow.entity.Booking;
import com.cineflow.entity.BookingSeat;
import com.cineflow.entity.ShowSeat;
import com.cineflow.entity.User;
import com.cineflow.enums.BookingStatus;
import com.cineflow.enums.PaymentStatus;
import com.cineflow.enums.ShowSeatStatus;
import com.cineflow.exception.CineflowException;
import com.cineflow.repository.BookingRepository;
import com.cineflow.repository.ShowSeatRepository;
import com.cineflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String processPayment(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
                new CineflowException("booking.not.found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new CineflowException("user.not.found"));

        if(!booking.getUser().getId().equals(currentUser.getId())){
            throw new CineflowException("unauthorized.action");
        }
        if(booking.getPaymentStatus()!= PaymentStatus.PENDING) {
            throw new CineflowException("payment.already.processed");
        }

        boolean success = Math.random() < 0.8;

        if(!success){
            List<ShowSeat> seats = booking.getBookingSeats().stream().map(
                    BookingSeat::getShowSeat).toList();

            for (ShowSeat seat : seats){
                seat.setStatus(ShowSeatStatus.AVAILABLE);
                seat.setLockedAt(null);
                seat.setLockedBy(null);
            }

            showSeatRepository.saveAll(seats);
            booking.setPaymentStatus(PaymentStatus.FAILED);
            bookingRepository.save(booking);
            return "payment.failed";
        }

        List<ShowSeat> seats = booking.getBookingSeats().stream().map(BookingSeat::getShowSeat).toList();

        for(ShowSeat seat : seats){
            seat.setStatus(ShowSeatStatus.BOOKED);
            seat.setLockedAt(null);
            seat.setLockedBy(null);
        }

        showSeatRepository.saveAll(seats);
        booking.setPaymentStatus(PaymentStatus.SUCCESS);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentId("PAY_"+System.currentTimeMillis());
        bookingRepository.save(booking);
        return "payment.successful";
    }
}
