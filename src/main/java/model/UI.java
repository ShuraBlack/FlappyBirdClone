package model;

import javaxt.io.Image;
import util.Constant;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static model.gamestate.State.*;

/**
 * Class which will be used to store UI values and render them
 */
public class UI {

    private int score;
    private int best;

    private static String playerColor;

    private final List<String> playerColors;

    /**
     * Basic constructor which creates an empty score/bestScore
     * and preload the different colors of the player graphic
     */
    public UI() {
        this.score = 0;
        this.best = 0;
        playerColor = "yellow";
        this.playerColors = new ArrayList<>();
        loadColors();
    }

    /**
     * Resets the player score
     */
    public void scoreReset() {
        this.score = 0;
    }

    /**
     * Increase the player score based on the difficulty
     * @param difficulty of the game
     */
    public void inreaseScore(String difficulty) {
        int point = 1;
        if (difficulty.equals("medium")) {
            point = 2;
        } else if (difficulty.equals("hard")) {
            point = 3;
        }
        this.score += point;
    }

    /**
     * Compare the score with the best score.
     * If the score is higher than the best score, it will be replaced
     */
    public void comparePoints() {
        if (this.score > this.best) {
            this.best = this.score;
        }
    }

    /**
     * @return the best player score
     */
    public int getBest() {
        return best;
    }

    /**
     * @return the player score
     */
    public int getScore() {
        return score;
    }

    /**
     * Loads the different colors of the player into the String list
     */
    private void loadColors() {
        playerColors.add("yellow");
        playerColors.add("blue");
        playerColors.add("green");
        playerColors.add("lightblue");
        playerColors.add("pink");
        playerColors.add("red");
    }

    /**
     * Changes the current color of the player
     * @param upOrDown decide if the new color should be before or after the old color
     */
    public void colorChange(boolean upOrDown) {
        int position = this.playerColors.indexOf(playerColor);
        if (upOrDown) {
            ++position;
            if (position > playerColors.size() - 1) {
                return;
            }
        } else {
            --position;
            if (position < 0) {
                return;
            }
        }
        playerColor = this.playerColors.get(position);
    }

    /**
     * Checks if the color change in the given direction is allowed or not
     * @param upOrDown decide if the new color should be before or after the old color
     * @return true if its allowed, otherwise false
     */
    public boolean possibleChange(boolean upOrDown) {
        int position = this.playerColors.indexOf(playerColor);
        if (upOrDown) {
            return position < playerColors.size() - 1;
        } else {
            return position > 0;
        }
    }

    /**
     * @return {@link Image} based on the current player color
     */
    public static Image getPlayerColorImage() {
        return ResourceManager.images.get("player_" + playerColor);
    }

    /**
     * Represents the start screen of the game
     * @param g Graphics of the application window
     * @param mode is the difficulty of the game
     * @param state is the current game state
     */
    public void startScreen(Graphics2D g, String mode, GameState state) {
        String stateName = "start";
        if (state == GameState.RESTART) {
            stateName = "restart";
        }
        Image screen = ResourceManager.images.get(stateName);
        g.drawImage(screen.getBufferedImage(),0,0,null);

        Image difficulty = ResourceManager.images.get(mode);
        g.drawImage(difficulty.getBufferedImage(),1560,-20,null);

        charSelect(g);
    }

    /**
     * Represents the player design select
     * @param g Graphics of the application window
     */
    public void charSelect(Graphics2D g) {
        Image playerColorImage = ResourceManager.images.get("player_" + playerColor);
        playerColorImage.setWidth(200);
        playerColorImage.setHeight(200);
        g.drawImage(playerColorImage.getBufferedImage(), Constant.WIDTH/2 - 120,Constant.HEIGHT/2 + 100,null);
        int position = playerColors.indexOf(playerColor);
        if (position > 0) {
            Image arrowLeft = ResourceManager.images.get("arrow_reverse");
            g.drawImage(arrowLeft.getBufferedImage(), Constant.WIDTH/2 - 250,Constant.HEIGHT/2 + 150,null);
        }
        if (position < this.playerColors.size() - 1) {
            Image arrowRight = ResourceManager.images.get("arrow");
            g.drawImage(arrowRight.getBufferedImage(), Constant.WIDTH/2 + 100,Constant.HEIGHT/2 + 150,null);
        }
    }

    /**
     * Represents the pause screen of the game
     * @param g Graphics of the application window
     * @param mode is the difficulty of the game
     */
    public void pauseScreen(Graphics2D g, String mode) {
        Image screen = ResourceManager.images.get("pause");
        g.drawImage(screen.getBufferedImage(),0,0,null);

        Image difficulty = ResourceManager.images.get(mode);
        g.drawImage(difficulty.getBufferedImage(),1560,-20,null);
    }

    /**
     * Renders the basic UI elements (Score and best score, including there Background)
     * @param g Graphics of the application window
     */
    public void render(Graphics2D g) {
        javaxt.io.Image score = ResourceManager.images.get("score");
        score.setHeight(50);
        score.setWidth(150);
        g.drawImage(score.getBufferedImage(),70,70,null);

        String scoreString = String.valueOf(this.score);
        int scoreRange = 73;
        for (int i = 0 ; i < scoreString.length() ; i++) {
            g.drawImage(ResourceManager.images.get(scoreString.charAt(i) + "").getBufferedImage(),scoreRange,95,null);
            scoreRange += 12;
        }

        Image best = ResourceManager.images.get("best");
        best.setHeight(50);
        best.setWidth(150);
        g.drawImage(best.getBufferedImage(),240,70,null);

        String bestString = String.valueOf(this.best);
        int bestRange = 243;
        for (int i = 0 ; i < bestString.length() ; i++) {
            g.drawImage(ResourceManager.images.get(bestString.charAt(i) + "").getBufferedImage(),bestRange,95,null);
            bestRange += 12;
        }
    }
}
