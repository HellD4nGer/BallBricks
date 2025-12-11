package game.objects;

import javax.media.opengl.GL;
import java.awt.Color;
import java.awt.Rectangle;

public class Brick {
    private int x, y, width, height;
    private Color color;
    private boolean destroyed = false;

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(GL gl) {
        if (destroyed) return;

        double midX = x + (width / 2.0);

        gl.glColor4f(0f, 0f, 0f, 0.3f);
        gl.glRecti(x + 3, y + 3, x + width + 3, y + height + 3);

        gl.glBegin(GL.GL_QUADS);

        setColor(gl, color.darker());
        gl.glVertex2d(x, y);
        gl.glVertex2d(x, y + height);

        setColor(gl, color.brighter());
        gl.glVertex2d(midX, y + height);
        gl.glVertex2d(midX, y);

        setColor(gl, color.brighter());
        gl.glVertex2d(midX, y);
        gl.glVertex2d(midX, y + height);

        setColor(gl, color.darker());
        gl.glVertex2d(x + width, y + height);
        gl.glVertex2d(x + width, y);
        gl.glEnd();

        gl.glLineWidth(1.0f);
        gl.glColor3f(0f, 0f, 0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2i(x, y);
        gl.glVertex2i(x + width, y);
        gl.glVertex2i(x + width, y + height);
        gl.glVertex2i(x, y + height);
        gl.glEnd();
    }

    private void setColor(GL gl, Color c) {
        gl.glColor3f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isDestroyed() { return destroyed; }
    public void destroy() { destroyed = true; }
}