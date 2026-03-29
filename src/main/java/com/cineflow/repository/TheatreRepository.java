package com.cineflow.repository;

import com.cineflow.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    boolean existsByNameAndLocation(String name, String location);
    List<Theatre> findByLocationIgnoreCase(String location);
}
