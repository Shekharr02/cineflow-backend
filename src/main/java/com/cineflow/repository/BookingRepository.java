package com.cineflow.repository;

import com.cineflow.entity.Booking;
import com.cineflow.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByPaymentStatus(PaymentStatus status);
}
