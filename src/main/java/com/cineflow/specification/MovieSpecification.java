package com.cineflow.specification;

import com.cineflow.entity.Movie;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MovieSpecification {

    public static Specification<Movie> filterMovies(
            String name,
            String genre,
            String language,
            Double rating
    ){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if(genre != null){
                predicates.add(cb.equal(root.get("genre"),genre));
            }

            if(language != null){
                predicates.add(cb.equal(root.get("language"),language));
            }

            if(rating != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), rating));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
