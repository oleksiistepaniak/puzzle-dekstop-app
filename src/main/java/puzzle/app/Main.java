package puzzle.app;

import puzzle.audio.BackgroundMusic;
import puzzle.audio.impl.BackgroundMusicImpl;
import puzzle.game.PuzzleGame;
import puzzle.game.impl.PuzzleGameImpl;
import puzzle.util.ImageSplitter;
import puzzle.util.impl.ImageSplitterImpl;

public class Main {
    public static void main(String[] args) {
        ImageSplitter imageSplitter = new ImageSplitterImpl();
        BackgroundMusic backgroundMusic = new BackgroundMusicImpl();
        PuzzleGame puzzleGame = new PuzzleGameImpl(imageSplitter, backgroundMusic);
        puzzleGame.loadGame();
    }
}
