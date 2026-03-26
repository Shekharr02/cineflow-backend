package com.cineflow.service;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import com.cineflow.entity.Movie;
import com.cineflow.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MovieResponse addMovie(MovieRequest request){
        Movie movie = modelMapper.map(request, Movie.class);
        Movie saved = movieRepository.save(movie);

        return modelMapper.map(saved, MovieResponse.class);
    }

    @Override
    public List<MovieResponse> getAllMovies(){
       return movieRepository.findAll()
               .stream()
               .map(movie -> modelMapper.map(movie, MovieResponse.class))
               .toList();
    }

    @Override
    public MovieResponse getMovieById(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Movie not found"));
        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieResponse> searchMovies (String title){
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(movie -> {
                    MovieResponse response = modelMapper.map(movie, MovieResponse.class);
                response.setLanguages(movie.getLanguages());
                return response;
                }).toList();
    }

    @Override
    public List<MovieResponse> getMoviesByGenre(String genre){
        return movieRepository.findByGenre(genre)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .toList();
    }

    @Override
    public List<MovieResponse> getMoviesByLanguage(String language){
        return movieRepository.findByLanguagesContaining(language)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .toList();
    }

    @Override
    public List<MovieResponse> getMoviesByRating(Double rating){
        return movieRepository.findByRatingGreaterThanEqual(rating)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .toList();
    }

    @Override
    public Page<MovieResponse> getAllMovies(int page, int size){
        Pageable pageable = PageRequest.of(page,size);

        Page<Movie> moviePage = movieRepository.findAll(pageable);

        return moviePage.map(movie->{
            MovieResponse response = modelMapper.map(movie, MovieResponse.class);
            response.setLanguages(movie.getLanguages());
            return response;
        });
    }
}
