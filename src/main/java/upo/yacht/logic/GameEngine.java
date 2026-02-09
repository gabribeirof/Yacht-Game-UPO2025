package upo.yacht.logic;

import upo.yacht.model.Player;
import upo.yacht.model.Die;
import upo.yacht.util.DiceManager;

import java.util.Scanner;

/* private final nao impede modificar os valores dentro do array.
Mas impede que eu substitua to do o array por um nnovo array.
Ex: em algum momento eu poderia tentar this.players = new Player[2], o que
substituiria o array antigo pelo novo.*/

//Classe que controla o jogo
public class GameEngine {

    private final Player[] players; //array de jogadores
    private final DiceManager diceManager; //objeto que representa um array de dados
    private final boolean isExtended; //flag que define a modalidade do jogo
    private final Scanner scanner; //variavel que pega dados do jogador: canal de comunicacao
    private int currentRound; //contador de 12 rodadas

    // Construtor: recebe como parametro True ou False, se True = modo variante, False = modo classico
    public GameEngine(boolean isExtended, int numPlayers, Long seed) {
        /*players nao sera inicializada no construtor porque precisa do nome dos jogadores que eh
        inserido pelo usuario, portanto sera inicializada no metodo starGame. */
        this.players = new Player[numPlayers];
        this.isExtended = isExtended;
        this.scanner = new Scanner(System.in);
        this.currentRound = 0; //jogo comeca na rodada 0 vai ate a 11.
        this.diceManager = new DiceManager(seed); //chama o construtor de DiceManager que inicializa os 5 dados
    }

    public void startGame() {
        setupPlayer();
        for (; currentRound <= 11; currentRound++) {
            System.out.println("\n=== ROUND " + currentRound + " ===");
            //Loop que percorre os jogadores, define de quem eh a vez de jogar
            for (Player p : players) {
                System.out.println("\nIt is: " + p.getName() + " turn.");
                executeTurn(p);
            }
        }
        finishGame();
    }

    //Metodo auxiliar(private, usado apenas por GameEngine) para definir o nome dos jogadores
    private void setupPlayer() {
        for (int i = 0; i < players.length; i++) {
            System.out.print("Type player " + (i + 1) + "'s name: ");
            String name = scanner.nextLine();
            this.players[i] = new Player(name);
        }
    }

    //Metodo auxiliar que destrava somente os dados que o usuario quer lancar.
    private boolean processRerrolChoices(String[] choices) {
        if (choices[0].equals("S")) {
            return true;
        }

        for (String s : choices) {
            if (s.matches("[0-4]")) {
                int index = Integer.parseInt(s);
                diceManager.getDie(index).setLocked(false);
            }
        }
        return false;
    }

    //Metodo auxiliar que mostra qual fase do extended mode estamos
    private void printPhaseHeader() {
        if (currentRound <= 3) {
            System.out.println(">>> MODE: DOWNWARD (3 Rolls, Fixed Category)");
        } else if (currentRound <= 7) {
            System.out.println(">>> MODE: 1ST ROLL (1 Roll, Choice Category)");
        } else {
            System.out.println(">>> MODE: FREE (3 Rolls, Choice Category)");
        }
    }

    //metodo que gerencia a vez do jogador de acordo com modo e fase.
    public void executeTurn(Player p) {
        diceManager.unlockAll(); // Reset inicial do turno
        int maxRolls;
        // Define o limite de lances se for extended mode: 1 para a fase '1st Roll', 3 para as demais
        // rodadas 5, 6, 7 e 8 so posso lancar o dado 1 vez
        if (isExtended) {
            maxRolls = (currentRound >= 4 && currentRound <= 7) ? 1 : 3;
        } else {
            maxRolls = 3;
        }

        for (int j = 0; j < maxRolls; j++) {
            diceManager.rollAvailableDice();
            diceManager.displayDice(); //mostra os dados lancados

            int rollsLeft = (maxRolls - 1) - j;
            if (rollsLeft == 0) {
                break;
            }

            if (isExtended) {
                printPhaseHeader();
            }

            // Em Downward (0-3) e Free (8-11), o jogador pode escolher os dados que quer lancar.
            System.out.println("Rolls left: " + rollsLeft);
            System.out.print("Which dice do you want to REROLL? (0-4), or 'S' to stay: ");

            String input = scanner.nextLine().toUpperCase();
            if (input.isEmpty()) {
                break;
            }
            String[] choices = input.split("[\\s,]+");


            diceManager.lockAll();
            if (processRerrolChoices(choices)) {
                break;
            }
        }

        // FASE DE PONTUAÇÃO
        handleScoring(p);
    }


    private void handleScoring(Player p) {
        int[] finalDice = diceManager.getDiceValues(); // Pega os números (ex: 1, 3, 3, 4, 6)
        int categoryIndex;

        // 1. Definição da Categoria
        if (isExtended && currentRound <= 3) { //ou seja, DOWNWARD
            // Fase DOWNWARD: Categoria é automática (Round 1 -> Cat 0, Round 2 -> Cat 1...)
            categoryIndex = currentRound;
            System.out.println("Downward Phase: Scoring automatically in category " + categoryIndex);
        } else {
            // Fases Normais: Jogador escolhe
            p.getScoreboard().displayBoard(); // Mostra o que já está preenchido
            System.out.print("Choose a category index (0-11): ");
            categoryIndex = Integer.parseInt(scanner.nextLine());
        }

        // 2. Cálculo (Aqui chamamos a classe Scorer que vamos criar)
        int points = Scorer.getScore(categoryIndex, finalDice);

        // 3. Registro no Placar
        try {
            p.getScoreboard().registerScore(categoryIndex, points);
            System.out.println("Points registered: " + points);
        } catch (IllegalStateException e) {
            System.out.println("Error: Category already filled! Choose another one.");
            handleScoring(p); // Recursão simples para tentar de novo se estiver ocupada
        }
    }

    public void finishGame() {
        System.out.println("End of the game. Calculating results ... ");
        // Aqui virá a lógica de salvar no arquivo .txt
    }
}






