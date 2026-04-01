package com.cineflow.service;

import com.cineflow.dto.ShowRequest;
import com.cineflow.dto.ShowResponse;

import java.util.List;

public interface ShowService {

    ShowResponse createShow (ShowRequest request);

    List<ShowResponse> getShowByMovie(Long movieId);
}
