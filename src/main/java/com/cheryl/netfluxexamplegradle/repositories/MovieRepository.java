package com.cheryl.netfluxexamplegradle.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cheryl.netfluxexamplegradle.domain.Movie;

/**
 * 
 * @author corcutt
 *
 */
// ReactiveMongoRepository is a MongoRepository that returns Spring Reactive types (Mono/Flux)
// Spring Data Mongo will give us implementation at runtime
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

}
