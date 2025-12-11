package game;

import game.logic.CollisionManager;
import game.logic.ScoreManager;
import game.logic.GameState;
import game.objects.Ball;
import game.objects.BrickGrid;
import game.objects.Paddle;
import game.storage.HighScoreSaver;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLCanvas;
import com.sun.opengl.util.GLUT;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends GLCanvas implements GLEventListener, KeyListener, ActionListener, MouseListener {

    private Timer timer;
    private GLU glu;
    private GLUT glut;
    private long lastTime = System.nanoTime();

    private SoundManager soundManager;

    private Ball ball1;
    private Paddle paddle1;
    private BrickGrid brickGrid1;
    private CollisionManager colManager1;
    private ScoreManager scoreManager1;
    private int lives1 = 3;
    private Ball ball2;
    private Paddle paddle2;
    private BrickGrid brickGrid2;
    private CollisionManager colManager2;
    private ScoreManager scoreManager2;
    private int lives2 = 3;
    private boolean p1Left, p1Right;
    private boolean p2Left, p2Right;
    private int gameWidth = 1300;
    private int gameHeight = 800;
    private boolean isMultiplayer = false;
    private String activeButton = null;

    public GamePanel() {
        this.addGLEventListener(this);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.setFocusable(true);
        this.requestFocus();

        timer = new Timer(16, this);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        if (soundManager != null) {
            soundManager.stopBackgroundMusic();
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        glu = new GLU();
        glut = new GLUT();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        if (getWidth() > 0) gameWidth = getWidth();
        if (getHeight() > 0) gameHeight = getHeight();

        soundManager = new SoundManager();
        soundManager.playBackgroundMusic();

        String mode = GameState.getPlayerMode();
        isMultiplayer = (mode != null && mode.equalsIgnoreCase("Multiplayer"));

        int speed = GameState.getBallSpeed();

        paddle1 = new Paddle(gameWidth/2 - 50, gameHeight - 50, 100, 15);
        ball1 = new Ball(gameWidth/2, gameHeight/2, 10, speed);
        brickGrid1 = new BrickGrid(5, 10, gameWidth);
        scoreManager1 = new ScoreManager();
        colManager1 = new CollisionManager(ball1, brickGrid1, scoreManager1, null);
        colManager1.setBottomPaddle(paddle1);
        lives1 = 3;

        if (isMultiplayer) {
            paddle2 = new Paddle(gameWidth/2 - 50, gameHeight - 50, 100, 15);
            ball2 = new Ball(gameWidth/2, gameHeight/2, 10, speed);
            brickGrid2 = new BrickGrid(5, 10, gameWidth);
            scoreManager2 = new ScoreManager();
            colManager2 = new CollisionManager(ball2, brickGrid2, scoreManager2, null);
            colManager2.setBottomPaddle(paddle2);
            lives2 = 3;
        }

        GameState.setStatus(GameState.Status.PLAYING);

        if (!timer.isRunning()) {
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;
        if (deltaTime > 0.05) deltaTime = 0.05;

        update(deltaTime);
        this.repaint();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        if (!isMultiplayer) {
            gl.glViewport(0, 0, getWidth(), getHeight());
            setupOrtho(gl, gameWidth, gameHeight);

            drawGameInstance(gl, paddle1, ball1, brickGrid1, lives1, scoreManager1, "P1");
            drawHUD(gl);
        } else {
            int w = getWidth();
            int h = getHeight();

            gl.glViewport(0, 0, w / 2, h);
            setupOrtho(gl, gameWidth, gameHeight);
            drawGameInstance(gl, paddle1, ball1, brickGrid1, lives1, scoreManager1, "P1 (WASD)");

            gl.glViewport(w / 2, 0, w / 2, h);
            setupOrtho(gl, gameWidth, gameHeight);
            drawGameInstance(gl, paddle2, ball2, brickGrid2, lives2, scoreManager2, "P2 (Arrows)");

            gl.glViewport(0, 0, w, h);
            setupOrtho(gl, gameWidth, gameHeight);
            gl.glColor3f(1f, 1f, 1f);
            gl.glLineWidth(4);
            gl.glBegin(GL.GL_LINES);
            gl.glVertex2i(gameWidth/2, 0);
            gl.glVertex2i(gameWidth/2, gameHeight);
            gl.glEnd();
            drawHUD(gl);
        }

        if (GameState.getStatus() == GameState.Status.PAUSED) {
            gl.glViewport(0, 0, getWidth(), getHeight());
            setupOrtho(gl, gameWidth, gameHeight);
            drawPauseMenu(gl);
        }
    }

    private void setupOrtho(GL gl, int w, int h) {
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, w, h, 0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void drawGameInstance(GL gl, Paddle p, Ball b, BrickGrid bg, int lives, ScoreManager sm, String label) {
        drawBackground(gl);
        if (p != null) p.draw(gl);
        if (b != null) b.draw(gl);
        if (bg != null) bg.draw(gl);

        for (int i = 0; i < lives; i++) {
            int bx = 20 + (i * 25);
            gl.glColor3f(1.0f, 0.2f, 0.2f);
            drawFan(gl, bx, 20, 8, 0, 360);
        }

        String realName;
        if (label.contains("P1")) {
            realName = GameState.getPlayerName();
        } else {
            realName = GameState.getPlayer2Name();
        }

        gl.glColor3f(1f, 1f, 0f);
        gl.glRasterPos2i(20, 50);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, realName + ": " + sm.getScore());
    }

    private void update(double deltaTime) {
        if (GameState.getStatus() != GameState.Status.PLAYING) return;

        double speed = 500.0;

        if (p1Left) paddle1.moveLeft(speed * deltaTime);
        if (p1Right) paddle1.moveRight(speed * deltaTime);
        paddle1.clamp(0, gameWidth);

        ball1.update(gameWidth, gameHeight, deltaTime);
        colManager1.checkCollisions(gameWidth, gameHeight);

        checkWinLose(1, ball1, brickGrid1);

        if (isMultiplayer) {
            if (p2Left) paddle2.moveLeft(speed * deltaTime);
            if (p2Right) paddle2.moveRight(speed * deltaTime);
            paddle2.clamp(0, gameWidth);

            ball2.update(gameWidth, gameHeight, deltaTime);
            colManager2.checkCollisions(gameWidth, gameHeight);

            checkWinLose(2, ball2, brickGrid2);
        }
    }

    private void checkWinLose(int playerID, Ball b, BrickGrid bg) {
        if (bg.allDestroyed()) {
            handleGameOver(true, "Player " + playerID);
        }
        if (b.getY() > gameHeight) {
            int correctSpeed = GameState.getBallSpeed();
            if (playerID == 1) {
                lives1--;
                if (lives1 <= 0) {
                    if (isMultiplayer) handleGameOver(true, "Player 2");
                    else handleGameOver(false, "Player 1");
                }
                else b.reset(gameWidth/2, gameHeight/2, correctSpeed);
            } else {
                lives2--;
                if (lives2 <= 0) handleGameOver(true, "Player 1");
                else b.reset(gameWidth/2, gameHeight/2, correctSpeed);
            }
        }
    }

    private void handleGameOver(boolean won, String winnerName) {
        if (timer != null) timer.stop();

        if (soundManager != null) soundManager.stopBackgroundMusic();

        GameState.setStatus(GameState.Status.GAMEOVER);
        String msg = won ? (winnerName + " WINS!") : "GAME OVER";

        int finalScore = 0;
        if (winnerName.contains("1")) finalScore = scoreManager1.getScore();
        else if (isMultiplayer) finalScore = scoreManager2.getScore();

        if (finalScore > 0) HighScoreSaver.saveScore(GameState.getPlayerName(), finalScore);

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        new GameOverDialog(topFrame, won, winnerName, finalScore).setVisible(true);
        if (topFrame != null) topFrame.dispose();
        new MainMenu().setVisible(true);
    }

    private void drawBackground(GL gl) {
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.5f, 0.8f, 1.0f); gl.glVertex2i(0, 0); gl.glVertex2i(gameWidth, 0);
        gl.glColor3f(0.2f, 0.5f, 0.8f); gl.glVertex2i(gameWidth, gameHeight); gl.glVertex2i(0, gameHeight);
        gl.glEnd();
    }

    private void fillRoundedRect(GL gl, float x, float y, float w, float h, float radius) {
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 180; i <= 270; i++) {
            double a = Math.toRadians(i); gl.glVertex2d(x+radius+Math.cos(a)*radius, y+radius+Math.sin(a)*radius);
        }
        for (int i = 270; i <= 360; i++) {
            double a = Math.toRadians(i); gl.glVertex2d(x+w-radius+Math.cos(a)*radius, y+radius+Math.sin(a)*radius);
        }
        for (int i = 0; i <= 90; i++) {
            double a = Math.toRadians(i); gl.glVertex2d(x+w-radius+Math.cos(a)*radius, y+h-radius+Math.sin(a)*radius);
        }
        for (int i = 90; i <= 180; i++) {
            double a = Math.toRadians(i); gl.glVertex2d(x+radius+Math.cos(a)*radius, y+h-radius+Math.sin(a)*radius);
        }
        gl.glEnd();
    }

    private void drawFan(GL gl, double cx, double cy, double r, int startAngle, int endAngle) {
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2d(cx, cy);
        for (int i = startAngle; i <= endAngle; i += 10) {
            double angle = Math.toRadians(i);
            gl.glVertex2d(cx + Math.cos(angle) * r, cy + Math.sin(angle) * r);
        }
        gl.glEnd();
    }

    private void drawHUD(GL gl) {
        int btnX = gameWidth - 50;
        int btnY = 10;
        int btnSize = 40;

        if ("HUD_PAUSE".equals(activeButton)) gl.glColor4f(0.3f, 0.3f, 0.3f, 0.8f);
        else gl.glColor4f(0.5f, 0.5f, 0.5f, 0.5f);

        fillRoundedRect(gl, btnX, btnY, btnSize, btnSize, 10);

        gl.glColor3f(1f, 1f, 1f);
        int offset = "HUD_PAUSE".equals(activeButton) ? 2 : 0;
        gl.glRecti(btnX + 12 + offset, btnY + 10 + offset, btnX + 17 + offset, btnY + 30 + offset);
        gl.glRecti(btnX + 23 + offset, btnY + 10 + offset, btnX + 28 + offset, btnY + 30 + offset);
    }

    private void drawPauseMenu(GL gl) {
        gl.glColor4f(0f, 0f, 0f, 0.7f);
        gl.glRecti(0, 0, gameWidth, gameHeight);

        int cx = gameWidth / 2;
        int cy = gameHeight / 2;

        int rX = cx - 100; int rY = cy - 60; int rW = 200; int rH = 50;
        if ("MENU_RESUME".equals(activeButton)) {
            gl.glColor3f(0f, 0.5f, 0f); fillRoundedRect(gl, rX+2, rY+2, rW-4, rH-4, 15);
        } else {
            gl.glColor3f(0f, 0.8f, 0f); fillRoundedRect(gl, rX, rY, rW, rH, 15);
        }
        gl.glColor3f(1f, 1f, 1f);
        gl.glRasterPos2i(cx - 40, cy - 25);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "RESUME");

        int eX = cx - 100; int eY = cy + 10; int eW = 200; int eH = 50;
        if ("MENU_EXIT".equals(activeButton)) {
            gl.glColor3f(0.5f, 0f, 0f); fillRoundedRect(gl, eX+2, eY+2, eW-4, eH-4, 15);
        } else {
            gl.glColor3f(0.8f, 0f, 0f); fillRoundedRect(gl, eX, eY, eW, eH, 15);
        }
        gl.glColor3f(1f, 1f, 1f);
        gl.glRasterPos2i(cx - 25, cy + 45);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "EXIT");

        gl.glColor3f(1f, 1f, 0f);
        gl.glRasterPos2i(cx - 50, cy - 100);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "PAUSED");
    }

    private void togglePause() {
        if (GameState.getStatus() == GameState.Status.PLAYING) GameState.setStatus(GameState.Status.PAUSED);
        else if (GameState.getStatus() == GameState.Status.PAUSED) GameState.setStatus(GameState.Status.PLAYING);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) togglePause();

        if (isMultiplayer) {

            if (code == KeyEvent.VK_A) p1Left = true;
            if (code == KeyEvent.VK_D) p1Right = true;

            if (code == KeyEvent.VK_LEFT) p2Left = true;
            if (code == KeyEvent.VK_RIGHT) p2Right = true;
        } else {
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) p1Left = true;
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) p1Right = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) p1Left = false;
        if (code == KeyEvent.VK_D) p1Right = false;
        if (code == KeyEvent.VK_LEFT) { if (isMultiplayer) p2Left = false; else p1Left = false; }
        if (code == KeyEvent.VK_RIGHT) { if (isMultiplayer) p2Right = false; else p1Right = false; }
    }

    public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX(); int my = e.getY();
        if (mx >= gameWidth - 50 && mx <= gameWidth - 10 && my >= 10 && my <= 50) { activeButton = "HUD_PAUSE"; return; }
        if (GameState.getStatus() == GameState.Status.PAUSED) {
            int cx = gameWidth / 2; int cy = gameHeight / 2;
            if (mx >= cx - 100 && mx <= cx + 100 && my >= cy - 60 && my <= cy - 10) activeButton = "MENU_RESUME";
            if (mx >= cx - 100 && mx <= cx + 100 && my >= cy + 10 && my <= cy + 60) activeButton = "MENU_EXIT";
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mx = e.getX(); int my = e.getY();
        if ("HUD_PAUSE".equals(activeButton)) {
            if (mx >= gameWidth - 50 && mx <= gameWidth - 10 && my >= 10 && my <= 50) togglePause();
        }
        if (GameState.getStatus() == GameState.Status.PAUSED) {
            int cx = gameWidth / 2; int cy = gameHeight / 2;
            if ("MENU_RESUME".equals(activeButton)) {
                if (mx >= cx - 100 && mx <= cx + 100 && my >= cy - 60 && my <= cy - 10) GameState.setStatus(GameState.Status.PLAYING);
            }
            if ("MENU_EXIT".equals(activeButton)) {
                if (mx >= cx - 100 && mx <= cx + 100 && my >= cy + 10 && my <= cy + 60) {

                    if (timer != null) timer.stop();
                    if (soundManager != null) soundManager.stopBackgroundMusic();

                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if(topFrame != null) topFrame.dispose();
                    new MainMenu().setVisible(true);
                }
            }
        }
        activeButton = null;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void reshape(GLAutoDrawable d, int x, int y, int w, int h) {}
    public void displayChanged(GLAutoDrawable d, boolean m, boolean dev) {}
}