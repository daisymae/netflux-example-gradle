package com.cheryl.netfluxexamplegradle;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ReactiveStreamsExample {

    @Test
    public void simpleStreamExample() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");
        dogs.toStream()
                .forEach(System.out::println);

    }

    @Test
    public void simpleStreamExample2() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");
        dogs.subscribe(System.out::println); // method is the subscriber
    }

    @Test
    public void simpleStreamExample3() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        // no output; because no subscribe action; see next example
        dogs.doOnEach(dog -> System.out.println(dog.get()));
    }

    @Test
    public void simpleStreamExample4() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        // trigger subscription
        dogs.doOnEach(dog -> System.out.println(dog.get())).subscribe();
    }

    @Test
    public void simpleStreamExample5WithSubscriber() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        // trigger subscription
        dogs.subscribe(((s) -> {
                    System.out.println(s);
                }),
                null,
                (() -> {
                    System.out.println("Woot! All Done");
                }));
    }

    @Test
    public void simpleStreamExample5WithSubscriberConsumers() throws Exception {
        // this example is to help visualize the one above
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        // subscriber lambda
        Consumer<String> println = System.out::println;

        // error handler
        Consumer<Throwable> errorHandler = e -> System.out.println("Some Error Occurred");

        // runnable upon complete
        Runnable allDone = () -> System.out.println("Woot! All Done!");

        // trigger subscription
        // this is equivalent to line 47 above
        dogs.subscribe(println, errorHandler, allDone);

    }

    @Test
    public void mapStreamExample() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        dogs.map(String::length)
                .doOnEach(System.out::println)
                .subscribe();
    }

    @Test
    public void filterStreamExample() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        dogs.filter(s -> s.length() == 6)
                .subscribe(System.out::println);
    }

    @Test
    public void filterAndLimitStreamExample() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        dogs.filter(s -> s.length() == 6)
                .take(2) // take is similar to limit
                .subscribe(System.out::println);
    }

    @Test
    public void filterAndSortStreamExample() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        dogs.filter(s -> s.length() == 6)
                .take(2) // take is similar to limit
                .sort()
                .subscribe(System.out::println);

    }

    @Test
    public void filterAndSortStreamWithCollectorExample() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        dogs.filter(s -> s.length() == 6)
                .take(2) // take is similar to limit
                .sort()
                .collect(Collectors.joining(", ")) // converts to Mono<String>
                .subscribe(System.out::println);

    }

    @Test
    public void testFlatMap() throws Exception {
        Flux<List<List<Integer>>> listFlux =
                Flux.just(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));

        // first iterable returns the two arrays
        // to get one list, need to flatten again
        listFlux.flatMap(lists -> Flux.fromIterable(lists))
                 .flatMap(lists -> Flux.fromIterable(lists))
                .subscribe(System.out::println);
    }

    @Test
    public void testFlatMap2() throws Exception {
        Flux<List<List<Integer>>> listFlux =
                Flux.just(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));

        // prettier? way
        // mixing Java 8 streams and reactive streams
        listFlux.flatMap(lists -> Flux.fromIterable(
                (lists.stream().flatMap(Collection::stream)
                ).collect(Collectors.toList())))
                .subscribe(System.out::println);
    }

    @Test
    public void testReduction() throws Exception {
        Flux<String> dogs = Flux.just("Vizsla", "Lab", "Golden", "GSP", "Poodle", "Yorkie", "Mutt");

        dogs.reduce((a,b) -> a + " - " + b).subscribe(System.out::println);
    }

}
