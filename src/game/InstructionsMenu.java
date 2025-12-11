package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InstructionsMenu extends JFrame {

    public InstructionsMenu() {
        setTitle("Instructions");
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(20, 20, 60),
                        0, getHeight(), new Color(0, 0, 0));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(255, 255, 255, 0));
        backButton.setBorder(new EmptyBorder(10, 15, 10, 15));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });

        topPanel.add(backButton);
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(0, 40, 40, 40));

        JLabel title = new JLabel("HOW TO PLAY");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(new Color(255, 215, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String instructionsHtml = "<html><div style='text-align: center; color: white; font-family: Arial; font-size: 14px;'>" +
                "<br><br>" +
                "<h2>OBJECTIVE</h2>" +
                "Break all the bricks by bouncing the ball off your paddle.<br>" +
                "Don't let the ball fall to the bottom!" +
                "<br><br>" +
                "<h2>SINGLE PLAYER CONTROLS</h2>" +
                "Use <b>Mouse</b> or <b>A / D</b> keys to move paddle." +
                "<br><br>" +
                "<h2>MULTIPLAYER CONTROLS</h2>" +
                "<b style='color:cyan;'>Player 1 (Left):</b> W / S keys (or A / D)<br>" +
                "<b style='color:orange;'>Player 2 (Right):</b> Arrow Keys" +
                "<br><br>" +
                "<h2>GAMEPLAY TIPS</h2>" +
                "&bull; Different bricks break differently!<br>" +
                "&bull; Catch the Power-Ups to win faster.<br>" +
                "&bull; Press <b>'P'</b> to Pause the game." +
                "</div></html>";

        JLabel textLabel = new JLabel(instructionsHtml);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(title);
        contentPanel.add(textLabel);

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
    }
}