package puzzle.util;

import java.awt.image.BufferedImage;
import java.util.List;
import puzzle.model.PuzzlePiece;

public interface ImageSplitter {
    List<PuzzlePiece> splitImage(BufferedImage image, int numRows, int numColumns);

    List<PuzzlePiece> getOriginalPuzzlePieces();
}
