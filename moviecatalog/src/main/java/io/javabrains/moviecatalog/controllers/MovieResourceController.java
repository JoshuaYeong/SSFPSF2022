package io.javabrains.moviecatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javabrains.moviecatalog.models.Movie;
import io.javabrains.moviecatalog.models.MovieSummary;

@RestController
@RequestMapping(path="/movies")
public class MovieResourceController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate template;
    
    @RequestMapping(path="/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = template.getForObject(
            "localhost:8080/movies/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
