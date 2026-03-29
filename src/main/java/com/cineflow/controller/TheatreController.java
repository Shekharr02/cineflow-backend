package com.cineflow.controller;

import com.cineflow.dto.TheatreRequest;
import com.cineflow.dto.TheatreResponse;
import com.cineflow.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
@RequiredArgsConstructor
public class TheatreController {
    private final TheatreService theatreService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheatreResponse> createTheatre(@Valid @RequestBody TheatreRequest request) {
        TheatreResponse response = theatreService.createTheatre(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TheatreResponse>> getAllTheatres(){
        List<TheatreResponse> theatres = theatreService.getAllTheatres();
        if(theatres.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<TheatreResponse>> getByCity(@PathVariable String city){
        return ResponseEntity.ok(theatreService.getByCity(city));
    }
}

