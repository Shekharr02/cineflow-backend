package com.cineflow.entity;

import com.cineflow.enums.ShowSeatStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"show_id", "seat_id"}))
@Getter
@Setter
public class ShowSeat {

    @Id
    @GeneratedValue
    private Long id;

    private double price;

    @ManyToOne
    @JsonIgnore
    private Show show;

    @ManyToOne
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private ShowSeatStatus status;
}
