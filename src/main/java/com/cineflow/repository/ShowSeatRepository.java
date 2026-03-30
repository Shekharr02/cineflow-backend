package com.cineflow.repository;

import com.cineflow.entity.ShowSeat;
import com.cineflow.enums.ShowSeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> { ;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeat s WHERE s.id IN :ids ORDER BY s.id")
    List<ShowSeat> findAllByIdWithLock(List<Long> ids);

    List<ShowSeat> findByStatus(ShowSeatStatus status);

    @Modifying
    @Transactional
    @Query("update ShowSeat s set s.status = 'AVAILABLE', s.lockedAt = null, s.lockedBy = null " +
            "where s.status = 'HELD' and s.lockedAt < :expiryTime")
    int releaseExpiredSeats(@Param("expiryTime")LocalDateTime expiryTime);
}
