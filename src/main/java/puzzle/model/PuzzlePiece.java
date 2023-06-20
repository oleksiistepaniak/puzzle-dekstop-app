package puzzle.model;

import java.awt.image.BufferedImage;
import javax.swing.JButton;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PuzzlePiece extends JButton {
    private Long id;
    private int horizontalCoordinate;
    private int verticalCoordinate;
    private BufferedImage puzzle;
}
