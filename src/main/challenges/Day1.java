package main.challenges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

public class Day1 implements Challenge {
    public static final Pattern NEW_LINE = Pattern.compile("\r\n\r\n");

    @Override
    public void part1() {
        Optional<Integer> max = NEW_LINE.splitAsStream(this.getInput())
                .map(this::getIntFromString)
                .reduce(Integer::max);

        max.ifPresent(y -> System.out.println("The top elf is carrying " + max.get() + " calories in total!"));
    }

    @Override
    public void part2() {
        int maxThree = NEW_LINE.splitAsStream(this.getInput())
                .map(this::getIntFromString)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(i -> i)
                .sum();

        System.out.println("The top three elves are carrying " + maxThree + " calories in total!");
    }

    private String getInput() {
        try {
            return Files.readString(Path.of("C:\\Workarea\\AdventOfCode\\src\\resources\\Day1.txt"));
        } catch (IOException e) {
            System.err.println("FEEEEEEEEHLER" + e);
            return null;
        }
    }

    private int getIntFromString(String string) {
        return Arrays.stream(string.split("\r\n"))
                .map(Integer::parseInt)
                .toList()
                .stream()
                .mapToInt(i -> i)
                .sum();
    }
}