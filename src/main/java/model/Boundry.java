package model;

import model.types.Part;
import util.Constant;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This Class is a collection of the different parts of the boundry
 */
public class Boundry {

    private final Part upperWall;
    private final Part pointZone;
    private final Part lowerWall;

    /**
     * Basic constructor which takes and X,Y and Height integer to calculate the positions of all 3 parts
     * @param x Position of upper wall
     * @param y Position of upper wall
     * @param height of upper wall
     */
    public Boundry(int x, int y, int height) {
        this.upperWall = new Wall(x,y,height);

        this.pointZone = new PointZone(upperWall.getX() + 70
                ,upperWall.getHeight()
                ,150);

        this.lowerWall = new Wall(upperWall.getX()
                ,upperWall.getHeight()+150
                ,Constant.HEIGHT - (upperWall.getHeight()+150));
    }

    /**
     * Calls the detectCollision function of the parts and return a String based on which parts collided
     * @param player is the current player object
     * @return "HIT" if they player collided into an {@link Wall}, "POINT" if he passed through a {@link PointZone}
     * or null if no collision exists
     */
    public String detectCollision(Player player) {
        if (upperWall.detectCollision(player)
                || lowerWall.detectCollision(player)) {
            return "HIT";
        }

        if (pointZone.detectCollision(player)) {
            return "POINT";
        }

        return null;
    }

    /**
     * Detect if the boundry is outside of the vision of the player
     * @return true if so, otherwise false
     */
    public boolean destoryBoundry() {
        if (upperWall.getX() < -80) {
            return true;
        }
        return false;
    }

    /**
     * Renders the {@link Wall} as an visible graphic
     * @param g Graphics of the application window
     * @param difficulty of the game
     */
    public void render(Graphics2D g, String difficulty) {
        if (difficulty.equals("rainbow")) {
            int number = ThreadLocalRandom.current().nextInt(1,6);
            g.drawImage(ResourceManager.images.get("pipe" + number).getBufferedImage()
                    ,upperWall.getX()
                    ,upperWall.getHeight() - 930
                    ,null);
        } else {
            g.drawImage(ResourceManager.images.get("pipe").getBufferedImage()
                    ,upperWall.getX()
                    ,upperWall.getHeight() - 930
                    ,null);
        }
    }

    /**
     * Calls the movePart() function of the Parts and pass them the parameter
     * @param amount defines how far the parts should move
     */
    public void moveParts(int amount) {
        upperWall.movePart(amount);
        pointZone.movePart(amount);
        lowerWall.movePart(amount);
    }

    /**
     * @return the upper wall of the boundry
     */
    public Part getUpperWall() {
        return upperWall;
    }
}
