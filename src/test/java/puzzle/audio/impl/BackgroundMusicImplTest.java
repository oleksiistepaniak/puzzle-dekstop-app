package puzzle.audio.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puzzle.audio.BackgroundMusic;
import puzzle.exception.BackgroundMusicException;

import static org.junit.jupiter.api.Assertions.*;

class BackgroundMusicImplTest {
    private static final long TIME_FOR_SLEEPING = 2000L;
    private BackgroundMusic backgroundMusic;

    @BeforeEach
    public void setUp() {
        backgroundMusic = new BackgroundMusicImpl();
    }

    @Test
    public void playBackgroundMusic_ok() {
        backgroundMusic.playBackgroundMusic();
        convertCurrentThreadToSleepMode();
        assertTrue(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void muteBackgroundMusic_ok() {
        backgroundMusic.playBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        assertFalse(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void enableBackgroundMusic_ok() {
        backgroundMusic.playBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        backgroundMusic.enableBackgroundMusic();
        convertCurrentThreadToSleepMode();
        assertTrue(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void playBackgroundMusicTwice_notOk() {
        backgroundMusic.playBackgroundMusic();
        assertThrows(BackgroundMusicException.class, () -> {
            convertCurrentThreadToSleepMode();
            backgroundMusic.playBackgroundMusic();
                }
        );
    }

    @Test
    public void muteBackgroundMusicWhenNotPlaying_ok() {
        backgroundMusic.muteBackgroundMusic();
        convertCurrentThreadToSleepMode();
        assertFalse(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void enableBackgroundMusicWhenNotPlaying_ok() {
        backgroundMusic.enableBackgroundMusic();
        assertFalse(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void muteBackgroundMusicTwice_ok() {
        backgroundMusic.playBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        assertFalse(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void enableBackgroundMusicTwice_ok() {
        backgroundMusic.playBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        backgroundMusic.enableBackgroundMusic();
        backgroundMusic.enableBackgroundMusic();
        convertCurrentThreadToSleepMode();
        assertTrue(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void muteAndEnableBackgroundMusic_ok() {
        backgroundMusic.playBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        backgroundMusic.enableBackgroundMusic();
        convertCurrentThreadToSleepMode();
        assertTrue(backgroundMusic.isMusicPlaying());
    }

    @Test
    public void enableAndMuteBackgroundMusic_ok() {
        backgroundMusic.playBackgroundMusic();
        backgroundMusic.enableBackgroundMusic();
        backgroundMusic.muteBackgroundMusic();
        assertFalse(backgroundMusic.isMusicPlaying());
    }

    private void convertCurrentThreadToSleepMode() {
        try {
            Thread.sleep(TIME_FOR_SLEEPING);
        } catch (InterruptedException e) {
            throw new RuntimeException("Current thread can't sleep", e);
        }
    }
}