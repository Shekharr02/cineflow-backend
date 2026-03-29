package com.cineflow.service;

import com.cineflow.dto.ScreenRequest;
import com.cineflow.dto.ScreenResponse;
import com.cineflow.entity.Screen;
import com.cineflow.entity.Seat;
import com.cineflow.entity.Theatre;
import com.cineflow.enums.SeatType;
import com.cineflow.repository.ScreenRepository;
import com.cineflow.repository.SeatRepository;
import com.cineflow.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService{
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public ScreenResponse createScreen(ScreenRequest request){
        if(screenRepository.existsByNameIgnoreCaseAndTheatreId(request.getName(), request.getTheatreId())){
            throw new RuntimeException("Screen already exists in theatre");
        }
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(()->
                        new RuntimeException("Theatre not found"));
        Screen screen = new Screen();
        screen.setName(request.getName());
        screen.setCapacity(request.getCapacity());
        screen.setTheatre(theatre);
        Screen savedScreen = screenRepository.save(screen);

        createSeatsForScreen(savedScreen);

        return mapToResponse(savedScreen);
    }

    @Override
    public List<ScreenResponse> getAllScreens(){
        return screenRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    private ScreenResponse mapToResponse(Screen screen) {
        ScreenResponse res = new ScreenResponse();
        res.setId(screen.getId());
        res.setName(screen.getName());
        res.setCapacity(screen.getCapacity());
        res.setTheatreId(screen.getTheatre().getId());
        return res;
    }

    @Override
    public ScreenResponse getScreenById(Long id){
        Screen screen = screenRepository.findById(id).orElseThrow(()->
                new RuntimeException("Screen not found"));
        return mapToResponse(screen);
    }
    private String generateRowLabel(int index){
        StringBuilder sb = new StringBuilder();
        while(index>=0){
            sb.insert(0,(char)('A'+(index%26)));
            index = index / 26-1;
        }
        return sb.toString();
    }
    private void createSeatsForScreen(Screen screen){
        int capacity = screen.getCapacity();
        int seatsPerRow = 15;

        int totalRows = (int) Math.ceil((double)capacity/seatsPerRow);

        List<Seat> seats = new ArrayList<>();
        int seatCount = 1;
        for(int i =0; i<totalRows && seatCount <= capacity; i++){
            String rowLabel = generateRowLabel(i);

            for(int j = 1; j<= seatsPerRow; j++){
                Seat seat = new Seat();
                seat.setSeatNumber(rowLabel + j);

                if(i >= totalRows -2) seat.setType(SeatType.RECLINER);
                else if(i >= totalRows -7) seat.setType(SeatType.PREMIUM);
                else seat.setType(SeatType.REGULAR);
                seat.setScreen(screen);
                seats.add(seat);
                seatCount++;
            }
        }
        seatRepository.saveAll(seats);
    }
}
