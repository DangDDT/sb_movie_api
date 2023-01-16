package com.example.movie.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Page<Movie> findByTitleContaining(String title, Pageable pageable);
}
