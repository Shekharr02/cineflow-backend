package com.cineflow.service;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import org.springframework.data.domain.Page;

public interface MovieService {

    MovieResponse addMovie(MovieRequest request);

    MovieResponse getMovieById(Long id);

    MovieResponse updateMovie(Long id, MovieRequest request);

    void deleteMovie(Long id);

    Page<MovieResponse> filterMovies(
            String name,
            String genre,
            String language,
            Double rating,
            int page,
            int size,
            String sortBy,
            String direction);
}
