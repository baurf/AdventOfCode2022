package main;

import main.challenges.Challenge;
import main.challenges.Day1;
import main.challenges.Day2;
import main.challenges.Day3;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdventOfCode {
    private static final Map<Integer, Challenge> challenges = new HashMap<>();

    public static void main(String[] args) {
        challenges.put(1, new Day1());
        challenges.put(2, new Day2());
        challenges.put(3, new Day3());

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        int day = -1;

        while (day == -1) {
            System.out.print("Enter the Day of the main.challenges.Challenge you want to start: ");
            day = scanner.nextInt();  // Read user input
            scanner.nextLine();
        }

        Challenge challenge = getChallenge(day);
        if (challenge == null) {
            System.err.print("main.challenges.Challenge not found." + day);
            System.exit(1);
        }

        challenge.part1();
        challenge.part2();
    }

    private static Challenge getChallenge(Integer key) {
        return challenges.get(key);
    }
}
