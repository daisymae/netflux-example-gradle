package com.cheryl.netfluxexamplegradle.service;

import com.cheryl.netfluxexamplegradle.domain.Movie;
import com.cheryl.netfluxexamplegradle.domain.MovieEvent;
import com.cheryl.netfluxexamplegradle.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

import static reactor.core.publisher.Flux.generate;

/**
 * author: corcutt
 */
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Flux<MovieEvent> events(String movieId) {
        return Flux.<MovieEvent>generate(movieEventSynchronousSink -> {
                movieEventSynchronousSink.next(new MovieEvent(movieId, new Date()));
        } ).delayElements(Duration.ofSeconds(1));
    }

    @Override
    public Mono<Movie> getMovieById(String id) {
        return this.movieRepository.findById(id);
    }

    @Override
    public Flux<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }
}
