package com.cineflow.service;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import com.cineflow.entity.Movie;
import com.cineflow.repository.MovieRepository;
import com.cineflow.specification.MovieSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public MovieResponse updateMovie(Long id, MovieRequest request){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(("Movie not found with id: "+id)));

        movie.setName(request.getName());
        movie.setGenre(request.getGenre());
        movie.setDuration(request.getDuration());
        movie.setRating(request.getRating());
        movie.setLanguages(request.getLanguages());
        movie.setCensorRating(request.getCensorRating());
        movie.setDescription(request.getDescription());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setImageUrl(request.getImageUrl());

        Movie updated = movieRepository.save(movie);

        MovieResponse response = modelMapper.map(updated, MovieResponse.class);

        response.setLanguages(updated.getLanguages());

        return response;
    }


    public Page<MovieResponse> filterMovies(String name,
                                            String genre,
                                            String language,
                                            Double rating,
                                            int page,
                                            int size,
                                            String sortBy,
                                            String direction){

        Sort sort = direction.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Movie> spec = MovieSpecification.filterMovies(name, genre, language, rating);

        Page<Movie> moviePage = movieRepository.findAll(spec, pageable);

        return moviePage.map(movie->
            modelMapper.map(movie, MovieResponse.class)
        );
    }
}
