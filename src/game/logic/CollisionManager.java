package game.logic;

import game.objects.Ball;
import game.objects.Brick;
import game.objects.BrickGrid;
import game.objects.Paddle;
import java.util.ArrayList;

public class CollisionManager {
    private Ball ball;
    private BrickGrid brickGrid;
    private ScoreManager scoreManager;
    private GameState gameState;

    private Paddle paddle;

    public CollisionManager(Ball ball, BrickGrid grid, ScoreManager sm, GameState gs) {
        this.ball = ball;
        this.brickGrid = grid;
        this.scoreManager = sm;
        this.gameState = gs;
    }

    public void setBottomPaddle(Paddle p) {
        this.paddle = p;
        System.out.println("CollisionManager: Paddle set successfully!");
    }

    public void checkCollisions(int w, int h) {
        // --- 1. Paddle Collision ---
        if (paddle != null) {
            if (ball.getBounds().intersects(paddle.getBounds())) {
                System.out.println("HIT: Paddle!");
                ball.bounceOffPaddle(paddle);

                ball.setY(paddle.getY() - ball.getRadius());
            }
        }
        ArrayList bricks = brickGrid.getBricks();

        for (int i = 0; i < bricks.size(); i++) {
            Brick b = (Brick) bricks.get(i);

            if (!b.isDestroyed()) {
                if (ball.getBounds().intersects(b.getBounds())) {
                    System.out.println("HIT: Brick!");
                    b.destroy();

                    if (scoreManager != null) {
                        scoreManager.addScore(10);
                    }

                    ball.reverseY();
                    break;
                }
            }
        }
    }
}