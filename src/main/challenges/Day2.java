package main.challenges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day2 implements Challenge {
    private static final Map<PLAY, PLAY> winsTo = new HashMap<>();

    public Day2() {
        winsTo.put(PLAY.ROCK, PLAY.SCISSORS);
        winsTo.put(PLAY.PAPER, PLAY.ROCK);
        winsTo.put(PLAY.SCISSORS, PLAY.PAPER);
    }

    @Override
    public void part1() {
        int result2 = getInput().stream().map(Day2::pointsFromStringOne).mapToInt(i -> i).sum();
        System.out.println("Tag 2, Teil 1: " + result2);
    }

    @Override
    public void part2() {
        int result2 = getInput().stream().map(Day2::pointsFromString).mapToInt(i -> i).sum();
        System.out.println("Tag 2, Teil 2: " + result2);
    }

    private static int pointsFromStringOne(String s) {
        List<String> splitString = splitStringBySpace(s);
        return getPointsFromPlays(
                PLAY.getPlayFromInput(splitString.get(0).charAt(0)),
                PLAY.getPlayFromInput(splitString.get(1).charAt(0))
        );
    }

    private static int pointsFromString(String s) {
        List<String> strings = splitStringBySpace(s);

        return getPointsDesiredOutcome(
                PLAY.getPlayFromInput(strings.get(0).charAt(0)),
                OUTCOME.getOutcomeFromInput(strings.get(1).charAt(0))
        );
    }

    private static List<String> splitStringBySpace(String s) {
        return Arrays.stream(s.split(" ")).toList();
    }

    private static int getPointsFromPlays(PLAY playOpponent, PLAY myPlay) {
        int points = myPlay.points;

        if (playOpponent == myPlay) {
            points += OUTCOME.DRAW.points;
        } else if (winsTo.get(playOpponent).equals(myPlay)) {
            points += OUTCOME.LOSE.points;
        } else if (winsTo.get(myPlay).equals(playOpponent)) {
            points += OUTCOME.WIN.points;
        } else {
            System.err.println("Uhm irgendwas isch falsch gloffe, das het kei Pünkt geh: " + playOpponent + " " + myPlay);
        }

        return points;
    }

    private static int getPointsDesiredOutcome(PLAY playOpponent, OUTCOME desiredOutcome) {
        int points = desiredOutcome.points;

        if (desiredOutcome == OUTCOME.WIN) {
            PLAY opponentBeats = winsTo.get(playOpponent);
            points += winsTo.get(opponentBeats).points;
        } else if (desiredOutcome == OUTCOME.DRAW) {
            points += playOpponent.points;
        } else if (desiredOutcome == OUTCOME.LOSE) {
            points += winsTo.get(playOpponent).points;
        } else {
            System.err.println("Woopsie han de Outcome ned gfunde?? " + playOpponent + desiredOutcome);
        }

        return points;
    }

    private static List<String> getInput() {
        try {
            return Files.readAllLines(Path.of("C:\\Workarea\\AdventOfCode\\src\\resources\\Day2.txt"));
        } catch (IOException e) {
            System.err.println("FEEEEEEEEHLER" + e);
            return Collections.emptyList();
        }
    }

}

enum PLAY {
    ROCK(List.of('A', 'X'), 1),
    PAPER(List.of('B', 'Y'), 2),
    SCISSORS(List.of('C', 'Z'), 3),
    UNKNOWN(List.of(), -1);

    public final int points;
    public final List<Character> validSymbols;

    PLAY(List<Character> validSymbols, int points) {
        this.validSymbols = validSymbols;
        this.points = points;
    }

    public static PLAY getPlayFromInput(Character input) {
        Optional<PLAY> possiblePlay = Arrays.stream(PLAY.values())
                .filter(i -> i.validSymbols.contains(input))
                .findAny();

        if (possiblePlay.isPresent()) {
            return possiblePlay.get();
        } else {
            System.err.println("Uhm de Zug gits ned?!: " + input);
            return possiblePlay.orElse(UNKNOWN);
        }

    }

}

enum OUTCOME {
    WIN('Z', 6),
    DRAW('Y', 3),
    LOSE('X', 0),
    UNKNOWN(' ', -1);
    public final int points;
    public final Character validSymbol;

    OUTCOME(char validSymbol, int points) {
        this.validSymbol = validSymbol;
        this.points = points;
    }

    public static OUTCOME getOutcomeFromInput(Character input) {
        Optional<OUTCOME> possiblePlay = Arrays.stream(OUTCOME.values())
                .filter(i -> i.validSymbol.equals(input))
                .findAny();

        if (possiblePlay.isPresent()) {
            return possiblePlay.get();
        } else {
            System.err.println("Uhm de Outcome gits ned?!: " + input);
            return possiblePlay.orElse(UNKNOWN);
        }
    }
}