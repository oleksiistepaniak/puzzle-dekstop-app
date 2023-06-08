package puzzle.audio.impl;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import puzzle.audio.BackgroundMusic;
import puzzle.exception.BackgroundMusicException;

public class BackgroundMusicImpl implements BackgroundMusic {
    private static final String PATH_TO_MUSIC_FILE = "src/main/resources/background_music.wav";
    private Clip clip;

    @Override
    public void playBackgroundMusic() {
        if (isMusicPlaying()) {
            throw new BackgroundMusicException("The song is already playing");
        }
        File musicFile = new File(PATH_TO_MUSIC_FILE);
        try (AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile)) {
            if (musicFile.exists()) {
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new BackgroundMusicException("Couldn't play background music "
                    + musicFile.getPath(), e);
        }
    }

    @Override
    public void muteBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    @Override
    public void enableBackgroundMusic() {
        if (clip == null) {
            playBackgroundMusic();
        }
        if (!clip.isRunning()) {
            clip.start();
        }
    }

    @Override
    public boolean isMusicPlaying() {
        return clip != null && clip.isRunning();
    }
}
