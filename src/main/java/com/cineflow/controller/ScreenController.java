package com.cineflow.controller;

import com.cineflow.dto.ScreenRequest;
import com.cineflow.dto.ScreenResponse;
import com.cineflow.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screens")
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScreenResponse> createScreen(@Valid @RequestBody ScreenRequest request){
        return ResponseEntity.status(201).body(screenService.createScreen(request));
    }

    @GetMapping
    public ResponseEntity<List<ScreenResponse>> getAllScreens(){
        List<ScreenResponse> screens = screenService.getAllScreens();
        if(screens.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(screens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponse> getScreenById(@PathVariable Long id){
        return ResponseEntity.ok(screenService.getScreenById(id));
    }
}
