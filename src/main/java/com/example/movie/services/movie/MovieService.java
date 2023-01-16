
package com.example.movie.services.movie;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.movie.models.Movie;

public interface MovieService {
    Page<Movie> getAllMovie(int pageIndex, int limit);

    Page<Movie> getMoviesByTitle(String title, int pageIndex, int limit);

    Optional<Movie> getMovieById(Integer id);

    Movie addMovie(Movie movie);

    // Movie deleteMovie(Integer movieId);
}
