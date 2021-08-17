package model.types;

import model.Player;

/**
 * Interface that will be used to union the different parts of a Boundry.
 * This isnt particularly needed
 */
public interface Part {

    /**
     * Detects collsion of the player and the current part
     * @param player is the current player object
     * @return true if they collid and false if they dont
     */
    boolean detectCollision(Player player);

    /**
     * @return X Position of the part
     */
    int getX();

    /**
     * @return Height of the part
     */
    int getHeight();

    /**
     * Moves the part in X position based on the given amount
     * @param amount defines how far the part should move
     */
    void movePart(int amount);
}
