package com.cineflow.service;

import com.cineflow.dto.TheatreRequest;
import com.cineflow.dto.TheatreResponse;
import com.cineflow.entity.Theatre;
import com.cineflow.exception.CineflowException;
import com.cineflow.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService{

    private final TheatreRepository theatreRepository;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public TheatreResponse createTheatre(TheatreRequest request){
        if(theatreRepository.existsByNameAndLocationIgnoreCase(request.getName(),request.getLocation())){
            throw new CineflowException("theatre.exists");
        }

        Theatre theatre = new Theatre();
        theatre.setName(request.getName());
        theatre.setLocation(request.getLocation());

        Theatre saved = theatreRepository.save(theatre);
        return new TheatreResponse(saved.getId(), saved.getName(), saved.getLocation());
    }

    @Override
    public List<TheatreResponse> getAllTheatres(){
        return theatreRepository.findAll()
                .stream()
                .map(t->new TheatreResponse(
                        t.getId(),
                        t.getName(),
                        t.getLocation()
                )).toList();
    }

    @Override
    public List<TheatreResponse> getByCity(String city){
        List<Theatre> theatres = theatreRepository.findByLocationIgnoreCase(city);
        if(theatres.isEmpty()) throw new CineflowException("theatre.not.foundInCity");

        return theatres.stream().map(this::mapToResponse).toList();
    }

    private TheatreResponse mapToResponse(Theatre theatre){
        return new TheatreResponse(
                theatre.getId(),
                theatre.getName(),
                theatre.getLocation()
        );
    }
}
