package com.cineflow.service;

import com.cineflow.dto.ScreenRequest;
import com.cineflow.dto.ScreenResponse;

import java.util.List;

public interface ScreenService {
    ScreenResponse createScreen(ScreenRequest request);

    List<ScreenResponse> getAllScreens();

    ScreenResponse getScreenById(Long id);
}
