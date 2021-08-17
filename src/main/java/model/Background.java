package model;

import javaxt.io.Image;

import java.awt.*;
import java.util.List;

/**
 * Handles the movment and storage of the Background image
 */
public class Background {
    List<Image> images;

    public Background(List<Image> images) {
        this.images = images;
    }

    /**
     * Renders the background image parts
     * @param g {@link Graphics2D graphic} will be used as the output
     */
    public void render(Graphics2D g) {
        for(int i = 0 ; i < 1920 ; i++) {
            g.drawImage(images.get(i).getBufferedImage(),i,0,null);
        }
    }

    /**
     * Updates the ordering of the background image parts. Based on the difficulty of the game.
     * Higher difficulty means faster movment of the background
     * @param difficulty of the game
     */
    public void update(String difficulty) {
        int repeats = 2;
        if (difficulty.equals("medium")) {
            repeats += 2;
        } else if (difficulty.equals("hard")) {
            repeats += 4;
        }

        for (int i = 0 ; i < repeats ; i++) {
            images.add(images.remove(0));
        }
    }
}
