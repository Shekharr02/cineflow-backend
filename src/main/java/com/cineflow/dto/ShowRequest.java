package com.cineflow.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowRequest {

    @NotNull(message = "{show.movieId.required}")
    private Long movieId;

    @NotNull(message = "{Screen ID is required}")
    private Long screenId;

    @NotNull(message = "{show.time.required}")
    @Future(message = "{show.time.future}")
    private LocalDateTime showTime;

    @NotBlank(message = "{show.language.required}")
    private String language;
}
