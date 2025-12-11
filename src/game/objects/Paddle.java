package game.objects;

import javax.media.opengl.GL;
import java.awt.Rectangle;

public class Paddle {
    private double x, y;
    private int width, height;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(GL gl) {
        double radius = height / 2.0;

        double straightWidth = width - (radius * 2);
        double straightStartX = x + radius;

        double tipWidth = straightWidth * 0.10;
        double centerWidth = straightWidth * 0.80;

        double sep1X = straightStartX + tipWidth;
        double sep2X = sep1X + centerWidth;
        double straightEndX = x + width - radius;

        float[] sides = {1.0f, 0.0f, 0.0f};
        float[] middle = {0.2f, 0.7f, 0.6f};


        gl.glColor3fv(sides, 0);
        drawFan(gl, straightStartX, y + radius, radius, 90, 270);
        drawFan(gl, straightEndX, y + radius, radius, 270, 450);

        drawCylinderSegment(gl, straightStartX, y, tipWidth, height, sides[0], sides[1], sides[2]);

        drawCylinderSegment(gl, sep1X, y, centerWidth, height, middle[0], middle[1], middle[2]);

        drawCylinderSegment(gl, sep2X, y, tipWidth, height, sides[0], sides[1], sides[2]);

        gl.glLineWidth(3.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(sep1X, y); gl.glVertex2d(sep1X, y + height);
        gl.glVertex2d(sep2X, y); gl.glVertex2d(sep2X, y + height);
        gl.glEnd();

        gl.glLineWidth(2.0f);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(x + radius*0.5, y + height*0.25);
        gl.glVertex2d(x + width - radius*0.5, y + height*0.25);
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

    private void drawCylinderSegment(GL gl, double px, double py, double w, double h, float r, float g, float b) {
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(r, g + 0.2f, b + 0.2f);
        gl.glVertex2d(px, py);
        gl.glVertex2d(px + w, py);

        gl.glColor3f(r, g, b);
        gl.glVertex2d(px + w, py + (h * 0.4));
        gl.glVertex2d(px, py + (h * 0.4));

        gl.glColor3f(r, g, b);
        gl.glVertex2d(px, py + (h * 0.4));
        gl.glVertex2d(px + w, py + (h * 0.4));

        gl.glColor3f(r * 0.6f, g * 0.6f, b * 0.6f);
        gl.glVertex2d(px + w, py + h);
        gl.glVertex2d(px, py + h);
        gl.glEnd();
    }

    public void moveLeft(double amount) { x -= amount; }
    public void moveRight(double amount) { x += amount; }

    public void clamp(int min, int max) {
        if (x < min) x = min;
        if (x + width > max) x = max - width;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}