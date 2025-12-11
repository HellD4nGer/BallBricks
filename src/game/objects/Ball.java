package game.objects;

import javax.media.opengl.GL;
import java.awt.Rectangle;

public class Ball {
    private double x, y;
    private int radius;
    private double dx, dy;
    private final int initialSpeed;

    public Ball(int x, int y, int radius, int speed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dy = -speed;
        this.dx = 3;
        this.initialSpeed = speed;
    }

    public void update(int width, int height, double deltaTime) {
        x += dx * deltaTime * 60;
        y += dy * deltaTime * 60;
        if (x - radius < 0 || x + radius > width) dx = -dx;
        if (y - radius < 0) dy = -dy;
    }

    public void bounceOffPaddle(Paddle paddle) {
        double speed = Math.sqrt(dx * dx + dy * dy);
        if (speed < initialSpeed) speed = initialSpeed;

        if (this.dx == 0) {
            int r = (int)(Math.random() * 3);
            if (r == 0) {
                this.dx = -(Math.random() * speed * 0.5);
            } else if (r == 1) {
                this.dx = Math.random() * speed * 0.5;
            } else {
                this.dx = 0;
            }
        } else if (this.dx < 0) {
            this.dx = -(Math.random() * speed * 0.8);
        } else {
            this.dx = Math.random() * speed * 0.8;
        }

        this.dy = -Math.abs(Math.sqrt(Math.abs(speed * speed - dx * dx)));
    }

    public void reverseY() { dy = -dy; }
    public void reverseX() { dx = -dx; }

    public void draw(GL gl) {
        int segments = 30;
        double angleStep = 2 * Math.PI / segments;

        gl.glColor4f(0f, 0f, 0f, 0.4f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < segments; i++) {
            double angle = i * angleStep;
            gl.glVertex2d(x + 2 + Math.cos(angle) * radius, y + 2 + Math.sin(angle) * radius);
        }
        gl.glEnd();

        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex2d(x - radius/3, y - radius/3);
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        for (int i = 0; i <= segments; i++) {
            double angle = i * angleStep;
            gl.glVertex2d(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius);
        }
        gl.glEnd();
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - radius), (int)(y - radius), radius * 2, radius * 2);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public int getRadius() { return radius; }

    public void reset(double centerX, double centerY, int speed) {
        this.x = centerX;
        this.y = centerY;
        this.dy = -speed;
        this.dx = (Math.random() > 0.5 ? 2 : -2);
    }
}