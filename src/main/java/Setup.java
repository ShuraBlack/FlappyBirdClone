import model.*;
import util.Constant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static model.gamestate.State.*;

/**
 * @since 27.05.2021
 * @author ShuraBlack
 * Part of my basic draw template.
 * Application procedure with drawing function
 */
public class Setup extends JPanel implements ActionListener {

    private final Background background;
    private final UI userInterface;

    private final Player player;

    private final List<Boundry> boundries = new ArrayList<>();
    private int distance = 300;
    private int lastScore = 0;

    private boolean RUNNING = false;
    private GameState state = GameState.START;
    private final Timer timer;

    private String difficulty = "easy";

    private final Setup INSTANCE;

    /**
     * Contructor for setup and basic configuration.
     * This also includes the EventListener for user input
     */
    public Setup() {
        INSTANCE = this;

        this.setPreferredSize(new Dimension(Constant.WIDTH,Constant.HEIGHT));
        this.setFocusable(true);

        userInterface = new UI();

        background = ResourceManager.loadResources();
        DifficultyManager.loadEasyDifficulty();
        ResourceManager.playMusic();

        player = new Player(200,Constant.HEIGHT/2);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // ESC
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                    // ESC while game is not running
                    if (!RUNNING) {
                        System.exit(0);

                    // ESC while game is running
                    } else {
                        if (state == GameState.PAUSED) {
                            state = GameState.NONE;
                            timer.restart();
                        } else if (state == GameState.NONE) {
                            state = GameState.PAUSED;
                            timer.stop();
                        }
                    }

                // SPACE while game is running
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && RUNNING && state == GameState.NONE) {
                    player.jump();

                // SPACE while game is not running
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !RUNNING) {

                    RUNNING = true;
                    state = GameState.NONE;

                    DifficultyManager.resetDifficulty(difficulty);
                    player.reset();
                    boundries.clear();
                    Boundry boundry = new Boundry(Constant.WIDTH-300,0,Constant.HEIGHT/2);
                    boundries.add(boundry);
                    userInterface.scoreReset();

                    timer.restart();
                    if (state == GameState.START) {
                        distance = 300;
                    }

                // UP while gmae is not running
                } else if (!RUNNING && e.getKeyCode() == KeyEvent.VK_UP) {

                    if (difficulty.equals("easy")) {
                        difficulty = "medium";
                        DifficultyManager.loadMediumDifficulty();
                        INSTANCE.draw(INSTANCE.getGraphics());
                        ResourceManager.playSound("Select");
                    } else if (difficulty.equals("medium")) {
                        difficulty = "hard";
                        DifficultyManager.loadHardDifficulty();
                        INSTANCE.draw(INSTANCE.getGraphics());
                        ResourceManager.playSound("Select");
                    } else if (difficulty.equals("hard") && userInterface.getBest() > 100) {
                        difficulty = "rainbow";
                        DifficultyManager.loadRainbowDifficulty();
                        INSTANCE.draw(INSTANCE.getGraphics());
                        ResourceManager.playSound("Select");
                    }

                // Down while gmae is not running
                } else if (!RUNNING && e.getKeyCode() == KeyEvent.VK_DOWN) {

                    switch (difficulty) {
                        case "medium":
                            difficulty = "easy";
                            DifficultyManager.loadEasyDifficulty();
                            INSTANCE.draw(INSTANCE.getGraphics());
                            ResourceManager.playSound("Select");
                            break;
                        case "hard":
                            difficulty = "medium";
                            DifficultyManager.loadMediumDifficulty();
                            INSTANCE.draw(INSTANCE.getGraphics());
                            ResourceManager.playSound("Select");
                            break;
                        case "rainbow":
                            difficulty = "hard";
                            DifficultyManager.loadHardDifficulty();
                            INSTANCE.draw(INSTANCE.getGraphics());
                            ResourceManager.playSound("Select");
                            break;
                    }

                // LEFT while gmae is not running
                } else if (!RUNNING && e.getKeyCode() == KeyEvent.VK_LEFT
                        && userInterface.possibleChange(false)) {
                    userInterface.colorChange(false);
                    INSTANCE.draw(INSTANCE.getGraphics());
                    ResourceManager.playSound("Select");

                // RIGHT while gmae is not running
                } else if (!RUNNING && e.getKeyCode() == KeyEvent.VK_RIGHT
                        && userInterface.possibleChange(true)) {
                    userInterface.colorChange(true);
                    INSTANCE.draw(INSTANCE.getGraphics());
                    ResourceManager.playSound("Select");
                }
            }
        });

        timer = new Timer(Constant.DELAY, this);
        timer.start();
    }

    /**
     * First access of the graphic component of the application window.
     * Used to redirect to the new {@link #update() update} and {@link #draw(Graphics) draw} function
     * @param g Graphics of the application window
     */
    public void paintComponent (Graphics g) {
        update();
        draw(g);
    }

    /**
     * New drawing function which will redirect the tasks to the render functions of all objects.
     * E.g {@link Player}, {@link Boundry}, {@link UI}
     * @param g Graphics of the application window
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        background.render(g2d);

        if (state == GameState.START || state == GameState.RESTART) {

            timer.stop();
            g.setColor(Color.BLACK);
            userInterface.startScreen((Graphics2D) g, this.difficulty, state);

        } else {

            player.render(g2d);
            for (Boundry boundry : boundries) {
                boundry.render(g2d, difficulty);
            }

            userInterface.render(g2d);
            if (state == GameState.PAUSED) {
                userInterface.pauseScreen(g2d, difficulty);
            }
        }
    }

    /**
     * Calculates all changes before a new Frame gets displayed.
     */
    public void update() {
        if (player.screenCollision()) {
            ResourceManager.playSound("Hit");
            RUNNING = false;
            state = GameState.RESTART;
            timer.stop();
            return;
        }

        background.update(this.difficulty);

        player.update();
        Boundry removeBoundry = null;

        for (Boundry boundry : boundries) {
            boundry.moveParts((int)DifficultyManager.difficultyMap.get("speed"));
            if (boundry.destoryBoundry()) {
                removeBoundry = boundry;
            }

            String collision = boundry.detectCollision(player);
            if (collision != null) {
                if (collision.equals("HIT")) {
                    ResourceManager.playSound("Hit");
                    RUNNING = false;
                    state = GameState.RESTART;
                    timer.stop();
                    return;
                } else if (collision.equals("POINT")) {
                    userInterface.inreaseScore(difficulty);
                    userInterface.comparePoints();
                }
            }
        }

        boundries.remove(removeBoundry);
        distance -= (int)DifficultyManager.difficultyMap.get("speed");

        if (distance >= (int)DifficultyManager.difficultyMap.get("distance")) {

            distance = 0;
            int last = boundries.get(boundries.size()-1).getUpperWall().getHeight();
            int newHeight = ThreadLocalRandom.current().nextInt(last-200,last+200);

            if (newHeight > Constant.HEIGHT-250) {
                newHeight = Constant.HEIGHT-400;
            } else if (newHeight < 0) {
                newHeight = 400;
            }

            boundries.add(new Boundry(Constant.WIDTH,0, newHeight));
        }

        if (userInterface.getScore() != 0 && userInterface.getScore() % 20 == 0 && lastScore != userInterface.getScore()) {
            DifficultyManager.increaseDifficulty();
            lastScore = userInterface.getScore();
        }
    }

    /**
     * Function that will update the application window in DELAY time (16ms = ~60FPS)
     * @param e ignored ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
