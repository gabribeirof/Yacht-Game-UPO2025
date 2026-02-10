package upo.yacht.util;

import upo.yacht.model.Die;

import java.util.Random;

public class DiceManager {
    //O objeto DiceManager eh um array de 5 dados.
    private final Die[] dice;

    //Construtor do array de dados
    public DiceManager(Long seed) {
        Random random = (seed != null) ? new Random(seed) : new Random();
        this.dice = new Die[5];
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die(random);
        }
    }

    // Rola todos os dados que não estão travados
    public void rollAvailableDice() {
        for (Die d : dice) {
            if (!d.isLocked()) {
                d.roll();
            }
        }
    }

    // Trava todos os dados
    public void lockAll() {
        for (Die d : dice) {
            d.setLocked(true);
        }
    }

    // Destrava todos os dados
    public void unlockAll() {
        for (Die d : dice) {
            d.setLocked(false);
        }
    }

    // Facilita o acesso a um dado específico
    public Die getDie(int index) {
        return dice[index];
    }

    public int[] getDiceValues() {
        int[] values = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            values[i] = dice[i].getValue();
        }
        return values;
    }

    // Mostra os dados na tela com os índices para o jogador se orientar
    public void displayDice() {
        System.out.print(
                "\n" +
                "------------------CURRENT DICE TABLE-------------------" +
                "\n" +
                "            ⟪    "
        );
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i].getValue() + "    ");
        }
        System.out.println(
                "⟫" +
                "\n" +
                "-------------------------------------------------------"
        );


    }
}