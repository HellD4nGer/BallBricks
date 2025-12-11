package game;

import game.logic.GameState;
import javax.swing.*;
import java.awt.*;

public class DifficultyMenu extends JFrame {

    public DifficultyMenu() {
        setTitle("Select Difficulty");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBackground(Color.BLACK);
        setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Difficulty", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        backgroundPanel.add(title, gbc);

        String[] buttons = {"Easy", "Medium", "Hard", "Back"};

        for (String text : buttons) {
            JButton button = createStyledButton(text);

            if (text.equals("Back")) {
                button.addActionListener(e -> {
                    dispose();
                    new PlayerModeMenu().setVisible(true);
                });
            } else {
                button.addActionListener(e -> {
                    GameState.setDifficulty(text);
                    String currentMode = GameState.getPlayerMode();
                    boolean isMulti = (currentMode != null && currentMode.equalsIgnoreCase("Multiplayer"));

                    String msg1 = isMulti ? "Enter Player 1 Name:" : "Enter Your Name:";
                    NameInputDialog dialog1 = new NameInputDialog(this, msg1);
                    dialog1.setVisible(true);
                    String name1 = dialog1.getResultName();

                    if (name1 == null) {
                        return;
                    }
                    if (name1.isEmpty()) {
                        name1 = "Player 1";
                    }
                    GameState.setPlayerName(name1);

                    if (isMulti) {
                        NameInputDialog dialog2 = new NameInputDialog(this, "Enter Player 2 Name:");
                        dialog2.setVisible(true);
                        String name2 = dialog2.getResultName();

                        if (name2 == null) {
                            return;
                        }
                        if (name2.isEmpty()) {
                            name2 = "Player 2";
                        }
                        GameState.setPlayer2Name(name2);
                    }

                    GameState.setStatus(GameState.Status.PLAYING);
                    dispose();
                    new GameWindow().setVisible(true);
                });
            }
            backgroundPanel.add(button, gbc);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        if (text.equals("Easy")) button.setForeground(Color.GREEN);
        if (text.equals("Hard")) button.setForeground(Color.RED);
        return button;
    }
}