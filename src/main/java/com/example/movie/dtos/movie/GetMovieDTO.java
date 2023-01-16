package com.example.movie.dtos.movie;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetMovieDTO {
    private int movieId;

    private String title;

    private int budget;

    private String homepage;

    private String overview;

    private int popularity;

    private Date releaseDate;

    private Long revenue;

    private int runtime;

    private String movieStatus;

    private String tagline;

    private int voteAverage;

    private int voteCount;
}
