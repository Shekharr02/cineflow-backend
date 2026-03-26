package com.cineflow.repository;

import com.cineflow.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long>, JpaSpecificationExecutor<Movie> {

}
