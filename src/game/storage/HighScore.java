package game.storage;

import java.io.Serializable;

public class HighScore implements Serializable, Comparable<HighScore> {
    private String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    public String toString() {
        return name + ": " + score;
    }

    @Override
    public int compareTo(HighScore other) {
        return Integer.compare(other.score, this.score);
    }
}