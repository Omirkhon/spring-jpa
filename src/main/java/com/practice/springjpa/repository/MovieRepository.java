package com.practice.springjpa.repository;

import com.practice.springjpa.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByRatingBetweenAndReleaseYearBetween(double minRating, double maxRating, int startYear, int endYear);

    List<Movie> findByReleaseYearIn(List<Integer> years);

    Page<Movie> findByRatingBetween(double minRating, double maxRating, Pageable pageable);

    Page<Movie> findByRatingBetweenAndReleaseYearBetween(
            double minRating, double maxRating, int startYear, int endYear, Sort sort, Pageable pageable);

    Page<Movie> findByTitleContainingAndRatingGreaterThanEqualAndReleaseYearGreaterThanEqualAndOrderByRatingDesc(
            String query, int minRating, int startYear, Sort sort, Pageable pageable);
}
