package game.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreSaver {
    private static final String FILE_NAME = "highscores.txt";
    private static final int MAX_SCORES = 10;

    public static void saveScore(String name, int score) {
        List<HighScore> scores = loadScores();
        scores.add(new HighScore(name, score));
        Collections.sort(scores);
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (HighScore hs : scores) {
                writer.write(hs.getName() + "," + hs.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HighScore> loadScores() {
        List<HighScore> scores = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return scores;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(new HighScore(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}