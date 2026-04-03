package com.cineflow.repository;

import com.cineflow.entity.Booking;
import com.cineflow.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByPaymentStatus(PaymentStatus status);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.user.id = :userId AND b.show.movie.id = :movieId AND b.status = 'CONFIRMED' AND b.show.showTime < CURRENT_TIMESTAMP")
    long countCompletedBookings(@Param("userId") Long userId, @Param("movieId") Long movieId);
}
