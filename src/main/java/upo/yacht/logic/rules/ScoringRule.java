package upo.yacht.logic.rules;
public interface ScoringRule {
    // Recebe os valores dos dados e retorna os pontos
    public abstract int calculate(int[] dice);
    // Retorna o nome da categoria (ex: "Full House")
    public abstract String getName();
}