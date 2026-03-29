package com.cineflow.controller;

import com.cineflow.dto.ShowRequest;
import com.cineflow.dto.ShowResponse;
import com.cineflow.entity.Show;
import com.cineflow.service.ShowService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody ShowRequest request) {
        return ResponseEntity.status(201).body(showService.createShow(request));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShows (@PathVariable @Min(1) Long movieId){
        List<ShowResponse> shows = showService.getShowByMovie(movieId);
        if(shows.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shows);
    }
}
