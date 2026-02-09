package upo.yacht.ui;

import upo.yacht.exceptions.YachtGameException;
import upo.yacht.logic.GameEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/* ENQUANTO GameEngine cuida da logica do jogo, ConsoleUI cuida das leituras
de arquivo, perguntas iniciais, etc */

public class ConsoleUI {
    private final Scanner scanner;
    private final boolean isExtended;
    private Long seed; // Store seed if provided via constructor or setter

    /**
     * Constructor that accepts a seed for deterministic gameplay.
     */
    public ConsoleUI(Long seed, boolean isExtended) {
        this.scanner = new Scanner(System.in);
        this.seed = seed;
        this.isExtended = isExtended;
    }

    /* QUAL SENTIDO DE UM SETSEED SE A SEED EH DEFINIDA EM LINHA DE COMANDO ?


     * Sets the random seed for the game.

    public void setSeed(Long seed) {
        this.seed = seed;
    } */

    public void start() {
        printWelcome();
        handleRules();

        int playerCount = askPlayerCount();

        /*Mode foi definido em linha de comando e esta contido na variavel
        isExtended. Se for true eh EXTENDED se for false eh CLASSIC
         */
        System.out.println("Mode: " + (isExtended ? "Extended" : "Classic"));


        // CREATE AND START THE ACTUAL GAME ENGINE
        GameEngine engine = new GameEngine(isExtended, playerCount, seed);
        engine.startGame();
    }

    private void printWelcome() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║        Welcome to Yacht!       ║");
        System.out.println("╚════════════════════════════════╝");
    }

    /* Funcao que pergunta se o jogador quer ler as regras, se sim,
    eh aberto um file com as regras e mostrado em tela.
     */
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

                // 1. O parseInt pode lançar NumberFormatException (exceção nativa)
                try {
                    numberOfPlayers = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Transformamos um erro genérico do Java em um YachtGameException
                    throw new YachtGameException("'" + input + "' is not a valid number.");
                }

                // 2. Verificação da regra de negócio: Minimo 2 jogadores
                if (numberOfPlayers < 2) {
                    throw new YachtGameException("The game requires at least 2 players to start.");
                }

            } catch (YachtGameException e) {
                //capturamos as excecoes personalizadas caso sejam lancadas.
                System.out.println("Error: " + e.getMessage());
            }
        }
        return numberOfPlayers;
    }
}