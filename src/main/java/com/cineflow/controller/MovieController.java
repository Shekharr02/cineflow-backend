package com.cineflow.controller;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import com.cineflow.service.MovieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@SecurityRequirement(name = "bearerAuth")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public MovieResponse addMovie(@RequestBody MovieRequest request){
        return movieService.addMovie(request);
    }


    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long id,
            @RequestBody MovieRequest request)
    {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return "movie.deleted";
    }

    @GetMapping
    public Page<MovieResponse> getMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Double rating,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        return movieService.filterMovies(name, genre, language, rating, page, size, sortBy, direction);
    }

}
