package com.cineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponse {
    private Long id;
    private String name;
    private int capacity;
    private Long theatreId;
}
