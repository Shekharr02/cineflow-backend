package com.cineflow.controller;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import com.cineflow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public MovieResponse addMovie(@RequestBody MovieRequest request){
        return movieService.addMovie(request);
    }

    @GetMapping
    public List<MovieResponse> getAllMovies (){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return "Movie deleted successfully";
    }

    @GetMapping("/search")
    public List<MovieResponse> searchMovies(@RequestParam String title){
        return movieService.searchMovies(title);
    }

    @GetMapping("/genre")
    public List<MovieResponse> getByGenre(@RequestParam String genre){
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping("/language")
    public List<MovieResponse> getByLanguage(@RequestParam String language){
        return movieService.getMoviesByLanguage(language);
    }

    @GetMapping("/rating")
    public List<MovieResponse> getByRating(@RequestParam double rating){
        return movieService.getMoviesByRating(rating);
    }
}
