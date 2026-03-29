package com.cineflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowResponse {
    private Long id;
    private String movieName;
    private String screenName;
    private LocalDateTime showTime;
}
