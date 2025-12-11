package game.logic;

public class GameState {

    public enum Status {
        PLAYING, PAUSED, GAMEOVER, MENU
    }

    private static Status currentStatus = Status.MENU;
    private static String difficulty = "medium";
    private static String playerMode = "1 Player";
    private static String playerName = "Player 1";
    private static String player2Name = "Player 2";
    private static int lives = 3;


    public static int getLives() { return lives; }

    public static void decreaseLives() {
        lives--;
    }

    public static void resetLives() {
        lives = 3;
    }

    public static void setDifficulty(String newDifficulty) {
        difficulty = newDifficulty;
        System.out.println("GameState: Difficulty set to " + difficulty);
    }
    public static String getDifficulty() { return difficulty; }

    public static void setPlayerMode(String mode) {
        playerMode = mode;
        System.out.println("GameState: Mode set to " + playerMode);
    }
    public static String getPlayerMode() { return playerMode; }

    public static void setPlayerName(String name) {
        playerName = name;
    }
    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayer2Name(String name) {
        player2Name = name;
    }
    public static String getPlayer2Name() {
        return player2Name;
    }


    public static void setStatus(Status newStatus) { currentStatus = newStatus; }
    public static Status getStatus() { return currentStatus; }

    public static int getBallSpeed() {
        if (difficulty.equalsIgnoreCase("easy")) return 3;
        if (difficulty.equalsIgnoreCase("hard")) return 9;
        return 6;
    }
}