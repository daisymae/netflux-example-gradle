package com.cheryl.netfluxexamplegradle.bootstrap;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cheryl.netfluxexamplegradle.domain.Movie;
import com.cheryl.netfluxexamplegradle.repositories.MovieRepository;

import reactor.core.publisher.Flux;

/**
 * Spring boot specific class. Implement as a component.
 * On startup, will execute the run method and pass in cmdline arguments.
 * For this project, not concerned with the commandline arguments.
 * 
 * @author corcutt
 *
 */
@Component
public class BootstrapCLR implements CommandLineRunner {

  private final MovieRepository movieRepository;
  
  // could have @Autowired here, but not needed for Spring 5
  // will automatically Autowire for us
  public BootstrapCLR(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }
  
  /**
   * The run method.
   * Installs movie "titles"
   * .map will take everything from the flux; create a new Movie using the title and generate a UUID
   * .flatMap will save on the movieRepository
   * then have a subscriber that will output to the console the movies
   * 
   */
  @Override
  public void run(String... args) throws Exception {
    // clear old data
    movieRepository.deleteAll().block();
    
    /*
     * :: in the following is a Method Reference. It is basically a reference to a single method. i.e. it refers to an existing method by name.
     * Method reference using :: is a convenience operator.
     * Method reference is one of the features belonging to Java lambda expressions.
     * Method reference can be expressed using the usual lambda expression syntax format using –>
     * In order to make it more simple :: operator can be used.
     */
    Flux.just("Silence of the Lambdas", "AEon Flux", "Enter the Mono<Void>", "The Fluxxinator",
        "Back to the Future", "Meet the Fluxes", "Lord of the Fluxes")
    .map(title -> new Movie(UUID.randomUUID().toString(), title))
    .flatMap(movieRepository::save)
    .subscribe(null, null, () -> {
      movieRepository.findAll().subscribe(System.out::println);
    });
  }

}
