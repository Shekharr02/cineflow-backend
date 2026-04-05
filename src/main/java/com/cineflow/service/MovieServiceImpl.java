package com.cineflow.service;

import com.cineflow.dto.MovieRequest;
import com.cineflow.dto.MovieResponse;
import com.cineflow.dto.RatingRequest;
import com.cineflow.dto.RatingResponse;
import com.cineflow.entity.Movie;
import com.cineflow.entity.MovieRating;
import com.cineflow.entity.User;
import com.cineflow.exception.CineflowException;
import com.cineflow.repository.BookingRepository;
import com.cineflow.repository.MovieRatingRepository;
import com.cineflow.repository.MovieRepository;
import com.cineflow.repository.UserRepository;
import com.cineflow.specification.MovieSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

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
                .orElseThrow(()-> new CineflowException("movie.not.available"));
        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    public void deleteMovie(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new CineflowException("movie.not.available"));
        movieRepository.deleteById(id);
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest request){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new CineflowException("movie.not.available"));

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

    @Override
    @Transactional
    public RatingResponse rateMovie(Long movieId, RatingRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CineflowException("user.not.found"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new CineflowException("movie.not.available"));

        if(movieRatingRepository.existsByUserIdAndMovieId(user.getId(), movieId)){
            log.warn("Rating failed: User ID {} already rated Movie ID {}", user.getId(), movieId);
            throw new CineflowException("movie.already.rated");
        }
        long validBookings = bookingRepository.countCompletedBookings(user.getId(),movieId);
        if(validBookings==0){
            log.warn("Rating failed: User ID {} has not watched Movie ID {}", user.getId(), movieId);
            throw new CineflowException("movie.not.watched");
        }

        MovieRating rating = new MovieRating();
        rating.setUser(user);
        rating.setMovie(movie);
        rating.setRatingValue(request.getRatingValue());
        rating.setReviewMessage(request.getReviewMessage());
        movieRatingRepository.save(rating);

        Double avgRating = movieRatingRepository.getAverageRatingForMovie(movieId);
        double roundedRating = movie.getRating();
        if(avgRating!=null) {
            roundedRating = Math.round(avgRating*10.0)/10.0;
            movie.setRating(roundedRating);
            movieRepository.save(movie);
        }
        log.info("User ID {} successfully rated Movie ID {} with {} stars.", user.getId(), movieId, request.getRatingValue());
        return new RatingResponse(
                movie.getId(),
                movie.getName(),
                rating.getRatingValue(),
                rating.getReviewMessage(),
                roundedRating
        );
    }

}
