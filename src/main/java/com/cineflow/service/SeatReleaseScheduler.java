package com.cineflow.service;

import com.cineflow.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class SeatReleaseScheduler {
    private final ShowSeatRepository showSeatRepository;

    @Scheduled(fixedRate = 30000)
    public void releaseExpiredSeats(){
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(5);

        int count = showSeatRepository.releaseExpiredSeats(expiryTime);

        if(count > 0){
            System.out.println("Released seat: "+count);
        }
    }
}
