package game;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Bouncing Ball");
        setSize(1300, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GamePanel panel = new GamePanel();
        add(panel);
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                panel.requestFocusInWindow();
            }
        });

        setLocationRelativeTo(null);
    }
}