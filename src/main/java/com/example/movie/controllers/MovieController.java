package com.example.movie.controllers;

import java.util.HashMap;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.movie.dtos.movie.GetMovieDTO;
import com.example.movie.dtos.movie.PostMovieDTO;
import com.example.movie.models.Movie;
import com.example.movie.services.movie.MovieService;
import com.example.movie.utils.response_handler.ResponseHandler;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Movie", description = "Movie APIs")
@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MovieService movieService;

    @GetMapping(value = "movies", params = { "pageIndex", "limit" })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> getsAllController(
            @RequestParam(value = "pageIndex", required = true) int pageIndex,
            @RequestParam(value = "limit", required = true) int limit) {
        final Page<GetMovieDTO> moviesPage = movieService.getAllMovie(pageIndex, limit)
                .map((Function<? super Movie, ? extends GetMovieDTO>) entity -> {
                    final GetMovieDTO dto = modelMapper.map(entity, GetMovieDTO.class);
                    return dto;
                });
        return ResponseHandler.generateResponse("Get movies successfully", HttpStatus.OK, moviesPage);
    }

    @GetMapping(value = "movies", params = { "pageIndex", "limit", "title" })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> getMoviesByTitleController(
            @RequestParam(value = "pageIndex", required = true) int pageIndex,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "title", required = false) String title) {
        final Page<GetMovieDTO> moviesPage = movieService.getMoviesByTitle(title, pageIndex, limit)
                .map((Function<? super Movie, ? extends GetMovieDTO>) entity -> {
                    final GetMovieDTO dto = modelMapper.map(entity, GetMovieDTO.class);
                    return dto;
                });
        return ResponseHandler.generateResponse("Get movies successfully", HttpStatus.OK, moviesPage);
    }

    @GetMapping("movies/{id}")
    public ResponseEntity<Object> getController(@PathVariable(name = "id") Integer id) {
        final GetMovieDTO objRes = movieService.getMovieById(id)
                .map((Function<? super Movie, ? extends GetMovieDTO>) entity -> {
                    final GetMovieDTO dto = modelMapper.map(entity, GetMovieDTO.class);
                    return dto;
                }).orElse(null);
        if (objRes == null) {
            return ResponseHandler.generateResponse("Movie is not found" + " {id=" + id + "}", HttpStatus.OK,
                    objRes);
        } else {
            return ResponseHandler.generateResponse("Get movie successfully", HttpStatus.OK, objRes);
        }
    }

    @PostMapping("movies")
    @ExceptionHandler({ HttpMessageNotReadableException.class, BadRequest.class })
    public ResponseEntity<Object> postOneController(@RequestBody PostMovieDTO newMovie) {
        Movie movie = modelMapper.map(newMovie, Movie.class);
        try {
            Movie addedMovie = movieService.addMovie(movie);
            Movie checkAdd = movieService.getMovieById(addedMovie.getMovieId()).orElse(null);
            if (checkAdd == null) {
                return ResponseHandler.generateResponse("Add movie failed", HttpStatus.OK, null);
            } else {
                HashMap<String, Integer> data = new HashMap<>();
                data.put("movieId", addedMovie.getMovieId());
                return ResponseHandler.generateResponse("Add movie successfully", HttpStatus.CREATED,
                        data);
            }
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(
                    "Add movie failed: Movie to add to valid -> " + e.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                    null);
        }
    }
}
