package com.cineflow.service;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;

import java.util.List;

public interface MovieService {

    MovieResponse addMovie(MovieRequest request);

    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id);

    void deleteMovie(Long id);

    List<MovieResponse> searchMovies(String title);

    List<MovieResponse> getMoviesByGenre(String genre);

    List<MovieResponse> getMoviesByLanguage(String genre);

    List<MovieResponse> getMoviesByRating(Double rating);
}
