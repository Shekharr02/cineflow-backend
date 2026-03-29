package com.cineflow.service;

import com.cineflow.dto.ShowRequest;
import com.cineflow.dto.ShowResponse;
import com.cineflow.entity.Show;

import java.util.List;

public interface ShowService {

    ShowResponse createShow (ShowRequest request);

    List<ShowResponse> getShowByMovie(Long movieId);
}
