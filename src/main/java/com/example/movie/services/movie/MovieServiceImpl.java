package com.example.movie.services.movie;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.movie.models.Movie;
import com.example.movie.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Page<Movie> getAllMovie(int pageIndex, int limit) {
        return movieRepository.findAll(PageRequest.of(pageIndex, limit));
    }

    @Override
    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Page<Movie> getMoviesByTitle(String title, int pageIndex, int limit) {
        return movieRepository.findByTitleContaining(title, PageRequest.of(pageIndex, limit));
    }

}