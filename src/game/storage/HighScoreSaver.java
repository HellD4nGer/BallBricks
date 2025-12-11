package game.storage;

import java.io.*;
import java.util.ArrayList;

public class HighScoreSaver {
    private static final String FILE_NAME = "highscores.dat";

    public static void saveScore(String name, int score) {
        ArrayList scores = loadScores();
        scores.add(new HighScore(name, score));

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(scores);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList loadScores() {
        ArrayList scores = new ArrayList();
        File f = new File(FILE_NAME);
        if (!f.exists()) return scores;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            scores = (ArrayList) ois.readObject();
            ois.close();
        } catch (Exception e) {
        }
        return scores;
    }
}