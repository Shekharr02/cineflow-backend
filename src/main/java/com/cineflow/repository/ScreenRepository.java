package com.cineflow.repository;

import com.cineflow.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    boolean existsByNameIgnoreCaseAndTheatreId(String name, Long theatreIdd);
}
