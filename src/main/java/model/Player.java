package model;

import javaxt.io.Image;
import util.Constant;

import java.awt.*;

/**
 * Player object which stores position, as well as physical properties of the player.
 */
public class Player {

    private final Vector pos;
    private final Vector vel;
    private final Vector acc;

    /**
     * Basic constructor which set the position of the player
     * and create (0,0) {@link Vector} for velocity and acceleration
     * @param x position of the player
     * @param y position of the player
     */
    public Player(int x, int y) {
        this.pos = new Vector(x,y);
        this.vel = new Vector();
        this.acc = new Vector();
    }

    /**
     * Resets all properties {@link Vector vectors} of the player
     */
    public void reset() {
        pos.mult(0);
        pos.add(new Vector(200,(double) Constant.HEIGHT/2));
        vel.mult(0);
        acc.mult(0);
    }

    /**
     * Apply force, as well as velocity and acceleration onto the position of the player
     */
    public void update() {
        applyForce();
        this.vel.add(this.acc);
        this.pos.add(this.vel);
        this.acc.mult(0);
    }

    /**
     * Renders the Player. This will load informations out of the {@link UI user interface} which color should be used
     * @param g Graphics of the application window
     */
    public void render(Graphics2D g) {
        Image player = UI.getPlayerColorImage().copy();

        player.setWidth(50);
        player.setHeight(50);
        player.rotate(vel.getY()*2);

        g.drawImage(player.getBufferedImage()
                ,(int) pos.x
                ,(int) pos.y
                ,null);
    }

    /**
     * Apply the "Jump" {@link Vector} onto the velocity and
     * play a sound through {@link model.ResourceManager#playSound(String)}
     */
    public void jump() {
        this.vel.mult(0);
        this.vel.add(new Vector(0,(int)DifficultyManager.difficultyMap.get("jump")));
        ResourceManager.playSound("Jump");
    }

    /**
     * Detects if the player collid with the top or bottom of the screen
     * @return true if he collid, otherwise false
     */
    public boolean screenCollision() {
        return pos.y > Constant.HEIGHT || pos.y < 0;
    }

    /**
     * @return Player position {@link Vector}
     */
    public Vector getPos() {
        return pos;
    }

    /**
     * Applys the {@link Constant#GRAVITY} force onto the acceleration of the player
     */
    private void applyForce() {
        this.acc.add(Constant.GRAVITY);
    }
}
