package com.practice.springjpa.controller;

import com.practice.springjpa.model.Movie;
import com.practice.springjpa.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping("filter-by-rating-year")
    public List<Movie> findByRatingBetweenAndYearBetween(@RequestParam double minRating, @RequestParam double maxRating, @RequestParam int startYear, @RequestParam int endYear) {
        return movieRepository.findByRatingBetweenAndReleaseYearBetween(minRating, maxRating, startYear, endYear);
    }

    @GetMapping("filter-by-years-in")
    public List<Movie> findByReleaseYearsIn(@RequestParam List<Integer> years) {
        return movieRepository.findByReleaseYearIn(years);
    }

    @GetMapping
    public List<Movie> findAll(@RequestParam int page, @RequestParam int size) {
        if (page < 1 || size < 1 || size > 50) {
            throw new RuntimeException();
        }
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Movie> pageResult = movieRepository.findAll(pageable);
        return pageResult.getContent();
    }

    @GetMapping("filter-by-rating")
    public List<Movie> findByRatingBetween(@RequestParam double minRating, @RequestParam double maxRating, @RequestParam int page, @RequestParam int size) {
        if (page < 1 || size < 1 || size > 50) {
            throw new RuntimeException();
        }
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Movie> pageResult = movieRepository.findByRatingBetween(minRating, maxRating, pageable);
        return pageResult.getContent();
    }

    @GetMapping("filter")
    public List<Movie> findByRatingAndReleaseYearBetweenSortedBy(@RequestParam double minRating, @RequestParam double maxRating,
                                                                 @RequestParam int startYear, @RequestParam int endYear, @RequestParam String sortBy, @RequestParam String sortOrder, @RequestParam int page, @RequestParam int size) {
        if (page < 1 || size < 1 || size > 50) {
            throw new RuntimeException();
        }

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Movie> pageResult = null;

        if (sortOrder.equalsIgnoreCase("asc")) {
            pageResult = movieRepository.findByRatingBetweenAndReleaseYearBetween(
                    minRating, maxRating, startYear, endYear, Sort.by(Sort.Direction.ASC, sortBy), pageable);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            pageResult = movieRepository.findByRatingBetweenAndReleaseYearBetween(
                    minRating, maxRating, startYear, endYear, Sort.by(Sort.Direction.DESC, sortBy), pageable);
        } else {
            throw new RuntimeException();
        }
        return pageResult.getContent();
    }

    @GetMapping("search") public List<Movie> findBySubqueryMinRatingAndStartYear(@RequestParam String query, @RequestParam int minRating, @RequestParam int startYear,
                                                              @RequestParam String sortBy, @RequestParam String sortOrder, @RequestParam int page, @RequestParam int size) {
        if (page < 1 || size < 1 || size > 50) {
            throw new RuntimeException();
        }
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Movie> pageResult = null;

        if (sortOrder.equalsIgnoreCase("asc")) {
            pageResult = movieRepository.findByTitleContainingAndRatingGreaterThanEqualAndReleaseYearGreaterThanEqualAndOrderByRatingDesc(
                    query, minRating, startYear,  Sort.by(Sort.Direction.ASC, sortBy), pageable);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            pageResult = movieRepository.findByTitleContainingAndRatingGreaterThanEqualAndReleaseYearGreaterThanEqualAndOrderByRatingDesc(
                    query, minRating, startYear, Sort.by(Sort.Direction.DESC, sortBy), pageable);
        } else {
            throw new RuntimeException();
        }
        return pageResult.getContent();

    }

}
