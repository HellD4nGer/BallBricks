package game;

import game.storage.HighScore;
import game.storage.HighScoreSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HighScoreMenu extends JFrame {

    public HighScoreMenu() {
        setTitle("High Scores");
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(Color.BLACK);
        setContentPane(backgroundPanel);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnBack = new JButton("←");
        btnBack.setFont(new Font("Arial", Font.BOLD, 20));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btnBack.setPreferredSize(new Dimension(50, 40));

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu().setVisible(true);
            }
        });

        JLabel title = new JLabel("Top Scores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        topBar.add(btnBack, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        backgroundPanel.add(topBar, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(20, 20, 20));
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ArrayList scores = (ArrayList) HighScoreSaver.loadScores();
        if (scores.isEmpty()) {
            textArea.setText("\n   No high scores yet!\n   Play a game to save one.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < scores.size(); i++) {
                HighScore hs = (HighScore) scores.get(i);
                sb.append(String.format(" %-15s : %d\n", hs.getName(), hs.getScore()));
            }
            textArea.setText(sb.toString());
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
    }
}