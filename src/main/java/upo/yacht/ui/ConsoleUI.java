package upo.yacht.ui;

import upo.yacht.logic.GameEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleUI {
    private final Scanner scanner;
    private Long seed; // Store seed if provided via constructor or setter

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.seed = null; // Default: no seed (random gameplay)
    }

    /**
     * Constructor that accepts a seed for deterministic gameplay.
     */
    public ConsoleUI(Long seed) {
        this.scanner = new Scanner(System.in);
        this.seed = seed;
    }

    /**
     * Sets the random seed for the game.
     */
    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public void start() {
        printWelcome();
        handleRules();

        int playerCount = askPlayerCount();

        // We don't need to ask for names here  - GameEngine will handle it
        // But we need to know if it's extended mode
        boolean isExtended = askGameStyle();

        // CREATE AND START THE ACTUAL GAME ENGINE
        GameEngine engine = new GameEngine(isExtended, playerCount, seed);
        engine.startGame();
    }

    private void printWelcome() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║        Welcome to Yacht!       ║");
        System.out.println("╚════════════════════════════════╝");
    }

    private void handleRules() {
        System.out.print("Do you want to read the rules? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.startsWith("y")) {
            try {
                String rules = Files.readString(Path.of("src/yacht_rules.txt"));
                System.out.println("\n--- RULES ---");
                System.out.println(rules);
                System.out.println("-------------\n");
            } catch (IOException e) {
                System.err.println("Error reading rules file: " + e.getMessage());
            }
        }
    }

    private int askPlayerCount() {
        int numberOfPlayers = 0;
        while (numberOfPlayers < 2) {
            System.out.print("How many players (minimum 2)? ");
            try {
                String input = scanner.nextLine();
                numberOfPlayers = Integer.parseInt(input);
                if (numberOfPlayers < 2) {
                    System.out.println("Please enter at least 2 players.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
        return numberOfPlayers;
    }

    private boolean askGameStyle() {
        System.out.println("\nSelect game style:");
        System.out.println("  [1] Classic");
        System.out.println("  [2] Extended Version");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equalsIgnoreCase("classic")) {
                System.out.println("Starting Classic mode...\n");
                return false; // Classic = not extended
            }
            if (input.equals("2") || input.equalsIgnoreCase("extended")) {
                System.out.println("Starting Extended mode...\n");
                return true; // Extended = true
            }

            System.out.println("Please enter '1' for Classic or '2' for Extended.");
        }
    }

}