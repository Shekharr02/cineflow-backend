package com.cineflow.service;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    MovieResponse addMovie(MovieRequest request);

    MovieResponse getMovieById(Long id);

    MovieResponse updateMovie(Long id, MovieRequest request);

    void deleteMovie(Long id);

    List<MovieResponse> searchMovies(String title);

    List<MovieResponse> getMoviesByGenre(String genre);

    List<MovieResponse> getMoviesByLanguage(String genre);

    List<MovieResponse> getMoviesByRating(Double rating);

    Page<MovieResponse> getAllMovies(int page, int size, String sortBy, String direction);
}
