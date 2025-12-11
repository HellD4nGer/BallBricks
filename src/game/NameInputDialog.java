package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class NameInputDialog extends JDialog {

    private JTextField nameField;
    private String inputtedName = null;

    public NameInputDialog(JFrame parent, String titleMessage) {
        super(parent, true);
        setUndecorated(true);

        Color startColor = new Color(70, 30, 140);
        Color endColor = new Color(30, 10, 60);
        Color borderColor = new Color(150, 100, 255);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 20, 20);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblMessage = new JLabel(titleMessage);
        lblMessage.setFont(new Font("Arial", Font.BOLD, 18));
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setMaximumSize(new Dimension(300, 35));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton btnOK = createStyledButton("OK", new Color(34, 139, 34));
        JButton btnCancel = createStyledButton("Cancel", new Color(178, 34, 34));

        btnOK.addActionListener(e -> {
            inputtedName = nameField.getText().trim();
            dispose();
        });

        btnCancel.addActionListener(e -> {
            inputtedName = null;
            dispose();
        });

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        mainPanel.add(lblMessage);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(nameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        setContentPane(mainPanel);
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setShape(new RoundRectangle2D.Double(0, 0, 350, 200, 20, 20));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(90, 35));
        return btn;
    }


    public String getResultName() {
        return inputtedName;
    }
}