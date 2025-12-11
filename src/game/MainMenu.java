package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Bouncing Ball - Main Menu");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 30, 60),
                        0, getHeight(), Color.BLACK);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel titleLabel = new JLabel("BALL BRICKS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 48));
        titleLabel.setForeground(Color.CYAN);

        backgroundPanel.add(titleLabel, gbc);

        backgroundPanel.add(Box.createVerticalStrut(20), gbc);

        JButton btnPlay = createFancyButton("PLAY GAME");
        JButton btnInstructions = createFancyButton("INSTRUCTIONS"); // <--- NEW BUTTON
        JButton btnHighScores = createFancyButton("HIGH SCORES");
        JButton btnSettings = createFancyButton("SETTINGS");
        JButton btnExit = createFancyButton("EXIT");

        btnPlay.addActionListener(e -> {
            new PlayerModeMenu().setVisible(true);
            dispose();
        });

        btnInstructions.addActionListener(e -> {
            new InstructionsMenu().setVisible(true);
            dispose();
        });

        btnHighScores.addActionListener(e -> {
            new HighScoreMenu().setVisible(true);
            dispose();
        });

        btnSettings.addActionListener(e -> {
            new SettingsMenu().setVisible(true);
            dispose();
        });

        btnExit.addActionListener(e -> System.exit(0));

        backgroundPanel.add(btnPlay, gbc);
        backgroundPanel.add(btnInstructions, gbc);
        backgroundPanel.add(btnHighScores, gbc);
        backgroundPanel.add(btnSettings, gbc);
        backgroundPanel.add(btnExit, gbc);
    }

    private JButton createFancyButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 150, 0));
                button.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(50, 50, 50));
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });

        return button;
    }
}