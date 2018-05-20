package com.cheryl.netfluxexamplegradle;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClosuresEffectivelyFInalAndLazyEval {

    @Test
    public void lambdaExample() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        numbers.stream()
                .map(number -> number * 2) // lambda is stateless
                .forEach(System.out::println);
    }

    @Test
    public void closureExample() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // externalize multiplier
        Integer multiplier = 2; // lexical scope

        numbers.stream()
                .map(number -> number * multiplier)
                // lambda 'closes over' variable in lexical scope
                // i.e. 'closure'
                .forEach(System.out::println);

    }

    @Test
    public void closureUsingFinal() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        final Integer multiplier = 2;

        Stream<Integer> numberStream = numbers.stream()
                .map(number -> number * multiplier);
        // try uncommenting out the next line to see what happens
        // when have a final value
        // multiplier = 3

        numberStream.forEach(System.out::println);

    }

    @Test
    public void closureEffectivelyFinal() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        Integer multiplier = 2; // effectively final

        Stream<Integer> numberStream = numbers.stream()
                .map(number -> number * multiplier);

        // see what happens when uncomment out the next line
        // get error; needs to be treated as if final
        // multiplier = 3;

        numberStream.forEach(System.out::println);

    }

    @Test
    public void breakingFinal() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        final Integer[] multiplier = {2};

        Stream<Integer> numberStream = numbers.stream()
                .map(number -> number * multiplier[0]);

        // legal to change values w/in the array
        // if change this to 0; doesn't run until it has to
        // even though the code above is doing the manipulation...
         multiplier[0] = 0;

         // it doesn't trigger until this line which needs the values
        // lazy evaluation
        // and because muiltiplier was changed before this line,
        // the new value will be used in the mapping
        numberStream.forEach(System.out::println);

    }


}
