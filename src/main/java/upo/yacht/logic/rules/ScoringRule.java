package upo.yacht.logic.rules;

public interface ScoringRule {
    public abstract int calculate(int[] dice); // Recebe os valores dos dados e retorna os pontos

    public abstract String getName();          // Retorna o nome da categoria (ex: "Full House")
}