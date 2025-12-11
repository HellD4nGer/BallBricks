package game.logic;

public class ScoreManager {
    private int score = 0;
    public void addScore(int s) { score += s; }
    public int getScore() { return score; }
}