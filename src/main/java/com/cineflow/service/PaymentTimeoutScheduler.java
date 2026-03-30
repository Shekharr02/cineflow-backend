package com.cineflow.service;

import com.cineflow.entity.Booking;
import com.cineflow.entity.BookingSeat;
import com.cineflow.entity.Show;
import com.cineflow.entity.ShowSeat;
import com.cineflow.enums.PaymentStatus;
import com.cineflow.enums.ShowSeatStatus;
import com.cineflow.repository.BookingRepository;
import com.cineflow.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentTimeoutScheduler {

    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expiredPendingPayments(){
        List<Booking> pendingBookings = bookingRepository.findByPaymentStatus(PaymentStatus.PENDING);
        for(Booking booking : pendingBookings){
            if (booking.getBookingTime().isBefore(LocalDateTime.now().minusMinutes(5))){
                List<ShowSeat> seats = booking.getBookingSeats().stream().map(BookingSeat::getShowSeat).toList();

                for(ShowSeat seat : seats){
                    seat.setStatus(ShowSeatStatus.AVAILABLE);
                    seat.setLockedAt(null);
                    seat.setLockedBy(null);
                }
                showSeatRepository.saveAll(seats);
                booking.setPaymentStatus(PaymentStatus.FAILED);
                bookingRepository.save(booking);
            }
        }
    }
}
