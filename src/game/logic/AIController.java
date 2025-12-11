package game.logic;

import game.objects.Ball;
import game.objects.Paddle;

public class AIController {
    private Paddle paddle;
    private Ball ball;
    private int speed = 4;

    public AIController(Paddle paddle, Ball ball) {
        this.paddle = paddle;
        this.ball = ball;
    }

    public void update() {
        double ballX = ball.getX();
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2;

        if (ballX > paddleCenter + 10) {
            paddle.moveRight(speed);
        } else if (ballX < paddleCenter - 10) {
            paddle.moveLeft(speed);
        }
    }
}