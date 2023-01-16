package com.example.movie.models;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
@Data
public class Movie implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
