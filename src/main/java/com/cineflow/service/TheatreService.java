package com.cineflow.service;

import com.cineflow.dto.TheatreRequest;
import com.cineflow.dto.TheatreResponse;
import com.cineflow.entity.Theatre;

import java.util.List;

public interface TheatreService {
    TheatreResponse createTheatre(TheatreRequest request);
    List<TheatreResponse> getAllTheatres();
    List<TheatreResponse> getByCity(String city);
}
