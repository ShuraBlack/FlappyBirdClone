package model;

import model.types.Part;

import java.awt.*;

/**
 * Point Zone object, which do not kill the player.
 * If the player collid with this object, he will get a point
 */
public class PointZone implements Part {

    private int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean hit = false;

    /**
     * Basic constructor which will store all informations in class variables.
     * Width of the rectangle is fixed
     * @param x Position of the PointZone (Top-left)
     * @param y Position of the PointZone (Top-left)
     * @param height of the PointZone
     */
    public PointZone(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = height;
    }

    /**
     * @return X Position (Top-left) of the rectangle
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * @return Height of the rectangle
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Moves the X position by the given amnount
     * @param amount defines how far the part should move
     */
    @Override
    public void movePart(int amount) {
        this.x = this.x + amount;
    }

    /**
     * Detects collisions with the given player
     * @param player is the current player object
     * @return true if they collid, otherwise false
     */
    @Override
    public boolean detectCollision(Player player) {
        if (hit) {
            return false;
        }
        Rectangle playerRect = new Rectangle((int) player.getPos().x
                ,(int) player.getPos().y
                ,40
                ,40);
        Rectangle object = new Rectangle(this.x
                ,this.y
                ,this.width
                ,this.height);

        boolean collision = playerRect.intersects(object);
        if (collision) {
            hit = true;
        }
        return collision;
    }
}
