package puzzle.game.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import puzzle.audio.BackgroundMusic;
import puzzle.game.PuzzleGame;
import puzzle.model.PuzzlePiece;
import puzzle.ui.PuzzleGameFrame;
import puzzle.ui.impl.PuzzleGameFrameImpl;
import puzzle.util.ImageSplitter;

public class PuzzleGameImpl implements PuzzleGame {
    public static final int NUM_ROWS = 4;
    public static final int NUM_COLUMNS = 4;
    private static final File FILE_OF_IMAGE = new File("src/main/resources/nature-image.jpeg");
    private final ImageSplitter puzzleImageSplitter;
    private final BackgroundMusic puzzleBackgroundMusic;

    public PuzzleGameImpl(ImageSplitter puzzleImageSplitter,
                          BackgroundMusic puzzleBackgroundMusic) {
        this.puzzleImageSplitter = puzzleImageSplitter;
        this.puzzleBackgroundMusic = puzzleBackgroundMusic;
    }

    @Override
    public PuzzleGameFrame loadGame() {
        BufferedImage image = loadImage();
        List<PuzzlePiece> puzzlePieces = puzzleImageSplitter.splitImage(image,
                NUM_ROWS, NUM_COLUMNS);
        List<PuzzlePiece> originalPuzzlePieces = puzzleImageSplitter.getOriginalPuzzlePieces();
        return new PuzzleGameFrameImpl(puzzlePieces, originalPuzzlePieces,
                puzzleImageSplitter, puzzleBackgroundMusic);
    }

    private BufferedImage loadImage() {
        try {
            return ImageIO.read(PuzzleGameImpl.FILE_OF_IMAGE);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load image "
                    + PuzzleGameImpl.FILE_OF_IMAGE.getPath(), e);
        }
    }
}
