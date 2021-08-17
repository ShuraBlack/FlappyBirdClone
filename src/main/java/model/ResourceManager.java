package model;

import javaxt.io.Image;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.*;

/**
 * Static class which stores all the needed images and play back sound files like .wav or .mp3
 */
public class ResourceManager {

    public static final Map<String, Image> images = new HashMap<>();

    /**
     * Load the {@link Image} into the images map.
     * Includes the splitting of the number sprite sheet and background image
     * @return the background image as finished object
     */
    public static Background loadResources() {
        images.put("player",new Image(new IconLoader("/images/Player.png").getImage()));
        images.put("background",new Image(new IconLoader("/images/Background.png").getImage()));
        images.put("pipe",new Image(new IconLoader("/images/Pipe.png").getImage()));
        images.put("score",new Image(new IconLoader("/images/Score.png").getImage()));
        images.put("start",new Image(new IconLoader("/images/Start.png").getImage()));
        images.put("restart",new Image(new IconLoader("/images/Restart.png").getImage()));
        images.put("pause",new Image(new IconLoader("/images/Pause.png").getImage()));
        images.put("easy",new Image(new IconLoader("/images/Easy.png").getImage()));
        images.put("medium",new Image(new IconLoader("/images/Medium.png").getImage()));
        images.put("hard",new Image(new IconLoader("/images/Hard.png").getImage()));
        images.put("best",new Image(new IconLoader("/images/Best.png").getImage()));
        images.put("rainbow",new Image(new IconLoader("/images/Rainbow.png").getImage()));

        images.put("pipe1",new Image(new IconLoader("/images/Pipe_1.png").getImage()));
        images.put("pipe2",new Image(new IconLoader("/images/Pipe_2.png").getImage()));
        images.put("pipe3",new Image(new IconLoader("/images/Pipe_3.png").getImage()));
        images.put("pipe4",new Image(new IconLoader("/images/Pipe_4.png").getImage()));
        images.put("pipe5",new Image(new IconLoader("/images/Pipe_5.png").getImage()));

        images.put("player_yellow",new Image(new IconLoader("/images/PlayerYellow.png").getImage()));
        images.put("player_blue",new Image(new IconLoader("/images/PlayerBlue.png").getImage()));
        images.put("player_green",new Image(new IconLoader("/images/PlayerGreen.png").getImage()));
        images.put("player_lightblue",new Image(new IconLoader("/images/PlayerLightBlue.png").getImage()));
        images.put("player_pink",new Image(new IconLoader("/images/PlayerPink.png").getImage()));
        images.put("player_red",new Image(new IconLoader("/images/PlayerRed.png").getImage()));

        Image arrow = new Image(new IconLoader("/images/Arrow.png").getImage());
        arrow.setHeight(100);
        arrow.setWidth(100);
        Image arrowReverse = arrow.copy();
        arrowReverse.rotate(180);
        images.put("arrow", arrow);
        images.put("arrow_reverse", arrowReverse);


        Image numberSheet = new Image(new IconLoader("/images/NumberSheet.png").getImage());
        int number = 0;
        for (int i = 0; i < 260; i += 26) {
            Image copy = numberSheet.copyRect(0,i,30,26);
            copy.setHeight(20);
            copy.setWidth(24);
            images.put(number + "",copy);
            number++;
        }

        List<Image> backgroundList = new ArrayList<>();
        for(int i = 0 ; i < 1920 ; i++) {
            backgroundList.add(images.get("background").copyRect(i,0,1,1080));
        }
        return new Background(backgroundList);
    }

    /**
     * Play-back short sound which are in .wav format
     * @param name of the sound file in the resource folder
     */
    public static synchronized void playSound(final String name) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = new SoundLoader("/sound/" + name + ".wav",false).getAudio();
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    /**
     * Play-back the music of the game
     */
    public static synchronized void playMusic() {
        new Thread(() -> {
            try {
                InputStream music = new SoundLoader("/sound/Theme.mp3",true).getBufferedAudio();
                Player player = new Player(music);
                player.play();

                boolean restart = true;
                while(restart) {
                    if (player.isComplete()) {
                        playMusic();
                        restart = false;
                    }
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
