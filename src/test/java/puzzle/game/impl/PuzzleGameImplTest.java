package puzzle.game.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puzzle.audio.BackgroundMusic;
import puzzle.audio.impl.BackgroundMusicImpl;
import puzzle.model.PuzzlePiece;
import puzzle.ui.PuzzleGameFrame;
import puzzle.ui.impl.PuzzleGameFrameImpl;
import puzzle.util.ImageSplitter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import puzzle.util.impl.ImageSplitterImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PuzzleGameImplTest {
    private static final String CORRECT_PATH_TO_IMAGE = "src/main/resources/nature-image.jpeg";
    private static final String INCORRECT_PATH_TO_IMAGE = "incorrect/path/to/image.jpeg";
    private static final int CORRECT_NUMBER_OF_ROWS = 4;
    private static final int CORRECT_NUMBER_OF_COLUMNS = 4;
    private static final int CORRECT_SIZE_OF_PUZZLES = 16;
    private PuzzleGameImpl puzzleGame;
    private ImageSplitter imageSplitter;

    @BeforeEach
    void setUp() {
        imageSplitter = new ImageSplitterImpl();
        BackgroundMusic backgroundMusic = new BackgroundMusicImpl();
        puzzleGame = new PuzzleGameImpl(imageSplitter, backgroundMusic);
    }

    @Test
    void loadGame_WithValidParameters_ok() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(CORRECT_PATH_TO_IMAGE));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load image", e);
        }

        List<PuzzlePiece> puzzlePieces = imageSplitter.splitImage(image,
                CORRECT_NUMBER_OF_ROWS, CORRECT_NUMBER_OF_COLUMNS);
        List<PuzzlePiece> originalPuzzlePieces = imageSplitter.getOriginalPuzzlePieces();
        PuzzleGameFrame gameFrame = puzzleGame.loadGame();
        assertNotNull(gameFrame);
        assertTrue(gameFrame instanceof PuzzleGameFrameImpl);
        assertEquals(puzzlePieces.size(), CORRECT_SIZE_OF_PUZZLES);
        assertEquals(originalPuzzlePieces.size(), CORRECT_SIZE_OF_PUZZLES);
    }

    @Test
    void loadGame_WithInvalidParameters_notOk() {
        assertThrows(RuntimeException.class, () -> {
            BufferedImage image;
            try {
                image = ImageIO.read(new File(INCORRECT_PATH_TO_IMAGE));
            } catch (IOException e) {
                throw new RuntimeException("Couldn't load image", e);
            }
            imageSplitter.splitImage(image, CORRECT_NUMBER_OF_ROWS, CORRECT_NUMBER_OF_COLUMNS);
            imageSplitter.getOriginalPuzzlePieces();
            puzzleGame.loadGame();
        });
    }
}
