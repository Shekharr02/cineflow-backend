package com.cineflow.service;

import com.cineflow.dto.ShowRequest;
import com.cineflow.dto.ShowResponse;
import com.cineflow.entity.*;
import com.cineflow.enums.ShowSeatStatus;
import com.cineflow.exception.CineflowException;
import com.cineflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService{

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    @Transactional
    public ShowResponse createShow(ShowRequest request){

        if (showRepository.existsByScreenIdAndShowTime(request.getScreenId(),request.getShowTime())){
            throw new CineflowException("screen.occupied");
        }
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(()-> new CineflowException("movie.not.available"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(()-> new CineflowException("screen.not.found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setShowTime(request.getShowTime());
        Show savedShow = showRepository.save(show);

        createShowSeats(savedShow);

        ShowResponse res = new ShowResponse();
        res.setId(savedShow.getId());
        res.setMovieName(savedShow.getMovie().getName());
        res.setScreenName(savedShow.getScreen().getName());
        res.setShowTime(savedShow.getShowTime());
        return res;
    }

    private void createShowSeats(Show show){
        List<Seat> seats = seatRepository.findByScreenId(show.getScreen().getId());
        List<ShowSeat> showSeats = new ArrayList<>();

        for(Seat seat : seats){
            ShowSeat ss = new ShowSeat();
            ss.setShow(show);
            ss.setSeat(seat);

            switch(seat.getType()){
                case RECLINER -> ss.setPrice(500);
                case PREMIUM -> ss.setPrice(300);
                case REGULAR -> ss.setPrice(150);
            }
            ss.setStatus(ShowSeatStatus.AVAILABLE);
            showSeats.add(ss);
        }
        showSeatRepository.saveAll(showSeats);
    }
    @Override
    public List<ShowResponse> getShowByMovie(Long movieId){
        List<Show> shows = showRepository.findByMovieId(movieId);
        return shows.stream().map(show->{
            ShowResponse res = new ShowResponse();
            res.setId(show.getId());
            res.setMovieName(show.getMovie().getName());
            res.setScreenName(show.getScreen().getName());
            res.setShowTime(show.getShowTime());
            return res;
        }).toList();
    }
}
