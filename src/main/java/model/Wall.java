package model;

import model.types.Part;

import java.awt.*;

/**
 * Wall object, which will kill the player when colliding
 */
public class Wall implements Part {

    private int x;
    private final int y;
    private final int width;
    private final int height;

    /**
     * Basic constructor which will store all informations in class variables.
     * Width of the rectangle is fixed
     * @param x Position of the Wall (Top-left)
     * @param y Position of the Wall (Top-left)
     * @param height of the Wall
     */
    public Wall(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.width = 80;
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
        Rectangle playerRect = new Rectangle((int) player.getPos().x
                ,(int) player.getPos().y + 7
                ,40
                ,40 - 5);

        Rectangle object = new Rectangle(this.x
                ,this.y
                ,this.width
                ,this.height);

        return playerRect.intersects(object);
    }
}
