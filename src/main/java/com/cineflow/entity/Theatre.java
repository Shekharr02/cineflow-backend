package com.cineflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatre", uniqueConstraints = @UniqueConstraint(columnNames = {"name","location"}),
        indexes = @Index(name = "idx_theatre_name_location", columnList = "name, location"))
@Getter
@Setter
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Screen> screens = new ArrayList<>();
}
