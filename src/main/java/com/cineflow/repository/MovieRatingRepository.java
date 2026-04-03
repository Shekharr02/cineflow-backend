package com.cineflow.repository;

import com.cineflow.entity.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

    @Query("SELECT AVG(m.ratingValue) FROM MovieRating m WHERE m.movie.id = :movieId")
    Double getAverageRatingForMovie(@Param("movieId") Long movieId);
}
