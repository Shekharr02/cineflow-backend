package com.cineflow.repository;

import com.cineflow.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findByGenre(String genre);

    List<Movie> findByLanguagesContaining(String language);

    List<Movie> findByRatingGreaterThanEqual(double rating);

    List<Movie> findByTitleContainingIgnoreCase(String title);
}
