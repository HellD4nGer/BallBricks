package game;

import game.logic.GameState;

import javax.swing.*;
import java.awt.*;

public class PlayerModeMenu extends JFrame {

    public PlayerModeMenu() {
        setTitle("Select Mode");
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

        JLabel title = new JLabel("Game Mode", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        backgroundPanel.add(title, gbc);

        String[] buttons = {"1 Player", "Multiplayer", "Back"};

        for (String text : buttons) {
            JButton button = createStyledButton(text);

            if (text.equals("Back")) {
                button.addActionListener(e -> {
                    dispose();
                    new MainMenu().setVisible(true);
                });
            } else {
                button.addActionListener(e -> {
                    System.out.println("Mode Selected: " + text);

                    GameState.setPlayerMode(text);
                    dispose();
                    new DifficultyMenu().setVisible(true);
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
        return button;
    }
}