package puzzle.util.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puzzle.exception.ImageSplitterException;
import puzzle.model.PuzzlePiece;
import puzzle.util.ImageSplitter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

class ImageSplitterImplTest {
    private static final String VALID_PATH_TO_IMAGE = "src/main/resources/nature-image.jpeg";
    private static final int VALID_NUMBER_OF_ROWS = 4;
    private static final int VALID_NUMBER_OF_COLUMNS = 4;
    private ImageSplitter imageSplitter;
    private BufferedImage testImage;

    @BeforeEach
    public void setUp() {
        imageSplitter = new ImageSplitterImpl();
        try {
            testImage = ImageIO.read(new File(VALID_PATH_TO_IMAGE));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read image. Path: " + VALID_PATH_TO_IMAGE);
        }
    }

    @Test
    public void splitImage_ValidImage_ok() {
        List<PuzzlePiece> puzzlePieces = imageSplitter.splitImage(testImage,
                VALID_NUMBER_OF_ROWS, VALID_NUMBER_OF_COLUMNS);

        assertEquals(VALID_NUMBER_OF_ROWS * VALID_NUMBER_OF_COLUMNS, puzzlePieces.size());

        int pieceWidth = testImage.getWidth() / VALID_NUMBER_OF_COLUMNS;
        int pieceHeight = testImage.getHeight() / VALID_NUMBER_OF_ROWS;
        for (PuzzlePiece piece : puzzlePieces) {
            assertEquals(pieceWidth, piece.getPuzzle().getWidth());
            assertEquals(pieceHeight, piece.getPuzzle().getHeight());
        }
    }

    @Test
    public void splitImage_InvalidImageSize_notOk() {
        int numRows = 10;
        int numColumns = 10;
        int maxWidthSize = 1500;
        int maxHeightSize = 1500;
        BufferedImage largeImage = new BufferedImage(
                maxWidthSize + 1,
                maxHeightSize + 1,
                BufferedImage.TYPE_INT_ARGB);
        assertThrows(ImageSplitterException.class, () -> imageSplitter
                .splitImage(largeImage, numRows, numColumns));
    }

    @Test
    public void getOriginalPuzzlePieces_ReturnsOriginalPieces_ok() {
        List<PuzzlePiece> puzzlePieces = imageSplitter.splitImage(testImage,
                VALID_NUMBER_OF_ROWS, VALID_NUMBER_OF_COLUMNS);
        List<PuzzlePiece> originalPieces = imageSplitter.getOriginalPuzzlePieces();

        assertEquals(puzzlePieces.size(), originalPieces.size());
        assertTrue(puzzlePieces.containsAll(originalPieces));
        assertTrue(originalPieces.containsAll(puzzlePieces));
    }
}
