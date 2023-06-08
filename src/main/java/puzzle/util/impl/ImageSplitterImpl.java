package puzzle.util.impl;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import puzzle.exception.ImageSplitterException;
import puzzle.model.PuzzlePiece;
import puzzle.util.ImageSplitter;

public class ImageSplitterImpl implements ImageSplitter {
    private static final int MAX_WIDTH_SIZE = 1500;
    private static final int MAX_HEIGHT_SIZE = 1500;
    private static final int MAX_NUMBER_OF_ROWS = 10;
    private static final int MAX_NUMBER_OF_COLUMNS = 10;
    private List<PuzzlePiece> originalPuzzlePieces;

    @Override
    public List<PuzzlePiece> splitImage(BufferedImage image, int numRows, int numColumns) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > MAX_WIDTH_SIZE || height > MAX_HEIGHT_SIZE || numRows > MAX_NUMBER_OF_ROWS
                || numColumns > MAX_NUMBER_OF_COLUMNS) {
            throw new ImageSplitterException("The passed image is too enormous."
                    + " The maximum allowed width is " + MAX_WIDTH_SIZE + " and the maximum"
                    + " allowed height is " + MAX_HEIGHT_SIZE);
        }
        int pieceWidth = width / numColumns;
        int pieceHeight = height / numRows;
        Long count = 0L;
        List<PuzzlePiece> puzzlePieces = new ArrayList<>();

        originalPuzzlePieces = new ArrayList<>();

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numColumns; x++) {
                BufferedImage pieceImage = new BufferedImage(pieceWidth,
                        pieceHeight, BufferedImage.TYPE_INT_ARGB);
                int[] pixels = new int[pieceWidth * pieceHeight];
                PixelGrabber pixelGrabber = new PixelGrabber(
                        image,
                        x * pieceWidth,
                        y * pieceHeight,
                        pieceWidth,
                        pieceHeight,
                        pixels,
                        0,
                        pieceWidth
                );
                try {
                    pixelGrabber.grabPixels();
                } catch (InterruptedException e) {
                    throw new ImageSplitterException("Failed to grab pixels from the image", e);
                }
                pieceImage.setRGB(0, 0, pieceWidth, pieceHeight, pixels, 0, pieceWidth);
                PuzzlePiece puzzlePiece = new PuzzlePiece();
                puzzlePiece.setId(count);
                puzzlePiece.setPuzzle(pieceImage);
                puzzlePieces.add(puzzlePiece);
                originalPuzzlePieces.add(puzzlePiece);
                count++;
            }
        }

        Collections.shuffle(puzzlePieces);

        return puzzlePieces;
    }

    @Override
    public List<PuzzlePiece> getOriginalPuzzlePieces() {
        return originalPuzzlePieces;
    }
}
