package game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GameOverDialog extends JDialog {

    public GameOverDialog(JFrame parent, boolean won, String winnerName, int score) {
        super(parent, true);
        setUndecorated(true);

        Color startColor, endColor, titleColor, scoreColor, buttonColor;
        String titleText;

        if (won) {
            titleText = winnerName + " WINS!";
            startColor = new Color(0, 50, 100);
            endColor = new Color(0, 0, 0);
            titleColor = new Color(255, 215, 0);
            scoreColor = Color.WHITE;
            buttonColor = new Color(34, 139, 34);
        } else {
            titleText = "GAME OVER";
            startColor = new Color(50, 0, 0);
            endColor = new Color(0, 0, 0);
            titleColor = new Color(220, 20, 60);
            scoreColor = Color.LIGHT_GRAY;
            buttonColor = new Color(178, 34, 34);
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);


                g2d.setColor(titleColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 30, 30);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));


        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(titleColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("Final Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(scoreColor);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 18));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(buttonColor);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setMaximumSize(new Dimension(100, 40));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        okButton.addActionListener(e -> dispose());

        mainPanel.add(okButton);

        setContentPane(mainPanel);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        setShape(new RoundRectangle2D.Double(0, 0, 400, 250, 30, 30));
    }
}