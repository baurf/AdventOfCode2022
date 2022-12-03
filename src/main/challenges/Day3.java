package main.challenges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Day3 implements Challenge {
    private static final int ASCII_UPPER_A = 64;
    private static final int ASCII_LOWER_A = 96;

    @Override
    public void part1() {
        int solution = getInput().stream()
                .map(Day3::charInBothCompartments)
                .map(Day3::getPriority)
                .mapToInt(i -> i)
                .sum();
        System.out.println("Tag 3, Teil 1: " + solution);
    }

    @Override
    public void part2() {
        final int chunkSize = 3;
        final AtomicInteger counter = new AtomicInteger();

        int solution2 = getInput().stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                .values()
                .stream()
                .map(Day3::charInAllThreeElves)
                .map(Day3::getPriority)
                .mapToInt(i -> i)
                .sum();

        System.out.println("Tag 3, Teil 2: " + solution2);
    }

    private static List<String> getInput() {
        try {
            return Files.readAllLines(Path.of("C:\\Workarea\\AdventOfCode\\src\\resources\\Day3.txt"));
        } catch (IOException e) {
            System.err.println("FEEEEEEEEHLER" + e);
            return Collections.emptyList();
        }
    }

    private static Integer charInBothCompartments(String input) {
        List<Integer> firstCompartment = input.substring(0, input.length() / 2).chars().boxed().toList();
        Optional<Integer> test = input.substring(input.length() / 2).chars().boxed()
                .filter(firstCompartment::contains).findFirst();
        if (test.isPresent()) {
            return test.get();
        } else {
            System.out.println("ned gfunde : " + input);
            return 0;
        }
    }

    private static Integer charInAllThreeElves(List<String> input) {
        List<Integer> chars2 = input.get(1).chars().boxed().toList();
        List<Integer> chars3 = input.get(2).chars().boxed().toList();

        Optional<Integer> test = input.get(0)
                .chars()
                .boxed()
                .filter(chars2::contains)
                .filter(chars3::contains)
                .findFirst();

        if (test.isPresent()) {
            return test.get();
        } else {
            System.out.println("ned gfunde 1: " + input.get(0) + "2: " + input.get(1) + "3: " + input.get(2));
            return 0;
        }
    }

    private static int getPriority(Integer itemInBoth) {
        int priority = 0;
        if (itemInBoth <= 122 & itemInBoth >= 97) {
            priority = itemInBoth - ASCII_LOWER_A;
        } else if (itemInBoth <= 90 & itemInBoth >= 65) {
            priority = itemInBoth - ASCII_UPPER_A + 26;
        } else {
            System.out.println("Whoopsie das Zeiche hani ned gfunde: " + itemInBoth);
        }
        return priority;
    }

}