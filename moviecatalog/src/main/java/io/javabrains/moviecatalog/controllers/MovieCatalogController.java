package io.javabrains.moviecatalog.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javabrains.moviecatalog.models.CatalogItem;
import io.javabrains.moviecatalog.models.Movie;
import io.javabrains.moviecatalog.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate template;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = template.getForObject(
            "localhost:8080/ratingsdata/users/" + userId, UserRating.class);

        return userRating.getRatings().stream()
            .map(rating -> {
                Movie movie = template.getForObject(
                    "localhost:8080/movies/" + rating.getMovieId(), Movie.class);

            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        })
            .collect(Collectors.toList());

    }
    
}