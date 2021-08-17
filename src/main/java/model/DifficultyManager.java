package model;

import util.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Static class which will be used as a storage for the different game difficulty settings
 */
public class DifficultyManager {

    public static Map<String,Number> difficultyMap = new HashMap<>();

    /**
     * Loads Easy difficulty settings into the Map and set the gravity constants
     */
    public static void loadEasyDifficulty() {
        difficultyMap.clear();
        difficultyMap.put("distance",600);
        difficultyMap.put("jump",-10);
        difficultyMap.put("gravity",0.8);
        difficultyMap.put("speed",-6);

        Constant.GRAVITY = new Vector(0, (double) DifficultyManager.difficultyMap.get("gravity"));
    }

    /**
     * Loads Medium difficulty settings into the Map and set the gravity constants
     */
    public static void loadMediumDifficulty() {
        difficultyMap.clear();
        difficultyMap.put("distance",500);
        difficultyMap.put("jump",-12);
        difficultyMap.put("gravity",1.0);
        difficultyMap.put("speed",-8);

        Constant.GRAVITY = new Vector(0, (double) DifficultyManager.difficultyMap.get("gravity"));
    }

    /**
     * Loads Hard difficulty settings into the Map and set the gravity constants
     */
    public static void loadHardDifficulty() {
        difficultyMap.clear();
        difficultyMap.put("distance",400);
        difficultyMap.put("jump",-13);
        difficultyMap.put("gravity",1.1);
        difficultyMap.put("speed",-10);

        Constant.GRAVITY = new Vector(0, (double) DifficultyManager.difficultyMap.get("gravity"));
    }

    /**
     * Loads Rainbow difficulty settings into the Map and set the gravity constants.
     * This difficulty function is not directly accessable and need to be unlocked in the current game session
     */
    public static void loadRainbowDifficulty() {
        difficultyMap.clear();
        difficultyMap.put("distance",400);
        difficultyMap.put("jump",-10);
        difficultyMap.put("gravity",0.8);
        difficultyMap.put("speed",-8);

        Constant.GRAVITY = new Vector(0, (double) DifficultyManager.difficultyMap.get("gravity"));
    }

    /**
     * Changes the difficulty of the current running game by increasing the speed of the {@link Boundry boundrys}
     */
    public static void increaseDifficulty() {
        difficultyMap.replace("speed",(int) difficultyMap.get("speed") - 1);
    }

    public static void resetDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                difficultyMap.replace("speed",-6);
                break;
            case "medium":
            case "rainbow":
                difficultyMap.replace("speed",-8);
                break;
            case "hard":
                difficultyMap.replace("speed",-10);
                break;
        }
    }
}
