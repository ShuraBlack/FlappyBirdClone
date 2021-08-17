package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @since 27.05.2021
 * @author ShuraBlack
 * Part of my basic draw template.
 * Loads sounds out of the resource folder. It is capable of loading {@link BufferedInputStream} or {@link AudioInputStream}
 * which can be later on used in {@link javax.sound.sampled.Clip}
 * This is just a temporarily object
 */
public class SoundLoader {

    private AudioInputStream audio;
    private BufferedInputStream bufferedAudio;

    public SoundLoader(String path, boolean directmode) {
        try {
            if (directmode) {
                bufferedAudio = new BufferedInputStream(getClass().getResourceAsStream(path));
            } else {
                audio = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(path)));
            }
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public BufferedInputStream getBufferedAudio() {
        return this.bufferedAudio;
    }

    public AudioInputStream getAudio() {
        audio.mark(0);
        return audio;
    }

}
