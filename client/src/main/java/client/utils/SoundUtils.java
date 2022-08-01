package client.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SoundUtils {

    private static File directory;
    private static File[] files;
    private static ArrayList<File> sounds;

    private static Media mediaMusic;
    private static MediaPlayer mediaPlayerMusic;
    public static double volumeMusic;

    private static Media mediaSFX;
    private static MediaPlayer mediaPlayerSFX;
    public static double volumeSFX;

    /**
     * Sets up everything to make the sound engine work and starts playing the background music.
     */
    public static void setupSound() {
        sounds = new ArrayList<File>();
        directory = new File("src/main/resources/sounds");
        files = directory.listFiles();

        if (files != null) {
            sounds.addAll(Arrays.asList(files));
        }

        mediaMusic = new Media(sounds.get(4).toURI().toString());
        mediaPlayerMusic = new MediaPlayer(mediaMusic);
        mediaPlayerMusic.setVolume(volumeMusic);
        mediaPlayerMusic.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerMusic.seek(Duration.ZERO);
                playMusic();
            }
        });
        playMusic();
    }

    /**
     * Translates the sound to be played into a correct index and then plays the corresponding sound.
     *
     * @param effect the wanted sound effect
     */
    public static void playSFX(String effect) {
        int number = 0;
        switch (effect) {
            case "click":
                number = 1;
                break;
            case "joker":
                number = 2;
                break;
            case "ticking":
                number = 3;
                break;
            case "gong":
                number = 4;
                break;
            default:
                break;
        }

        mediaSFX = new Media(sounds.get(number - 1).toURI().toString());
        mediaPlayerSFX = new MediaPlayer(mediaSFX);
        mediaPlayerSFX.setVolume(volumeSFX);
        mediaPlayerSFX.play();
    }

    /**
     * Starts the background music player.
     */
    public static void playMusic() {
        mediaPlayerMusic.play();
    }

    /**
     * Pauses the background music player.
     */
    public static void pauseMusic() {
        mediaPlayerMusic.pause();
    }

    /**
     * Updates the volume of the background music.
     */
    public static void setMusicVolume() {
        mediaPlayerMusic.setVolume(volumeMusic);
    }
}
