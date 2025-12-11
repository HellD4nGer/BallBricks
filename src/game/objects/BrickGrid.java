package game.objects;

import javax.media.opengl.GL;
import java.awt.Color;
import java.util.ArrayList;

public class BrickGrid {
    private ArrayList bricks = new ArrayList();

    public BrickGrid(int rows, int cols, int screenWidth) {
        int brickWidth = 70;
        int brickHeight = 20;
        int padding = 5;
        int offsetX = (screenWidth - (cols * (brickWidth + padding))) / 2;
        int offsetY = 50;

        for (int row = 0; row < rows; row++) {
            Color c = (row % 2 == 0) ? Color.RED : Color.ORANGE;
            for (int col = 0; col < cols; col++) {
                int x = offsetX + col * (brickWidth + padding);
                int y = offsetY + row * (brickHeight + padding);
                bricks.add(new Brick(x, y, brickWidth, brickHeight, c));
            }
        }
    }

    public void draw(GL gl) {
        for (int i = 0; i < bricks.size(); i++) {
            Brick b = (Brick) bricks.get(i);
            b.draw(gl);
        }
    }
    public boolean allDestroyed() {
        for (int i = 0; i < bricks.size(); i++) {
            Brick b = (Brick) bricks.get(i);
            if (!b.isDestroyed()) {
                return false;
            }
        }
        return true;
    }


    public ArrayList getBricks() {
        return bricks;
    }
}