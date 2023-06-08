package puzzle.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

public class PuzzlePiece extends JButton {
    private Long id;
    private int horizontalCoordinate;
    private int verticalCoordinate;
    private BufferedImage puzzle;
    private int initialHorizontalCoordinate;
    private int initialVerticalCoordinate;

    public PuzzlePiece() {
        initializeMouseListener();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHorizontalCoordinate() {
        return horizontalCoordinate;
    }

    public void setHorizontalCoordinate(int horizontalCoordinate) {
        this.horizontalCoordinate = horizontalCoordinate;
    }

    public int getVerticalCoordinate() {
        return verticalCoordinate;
    }

    public void setVerticalCoordinate(int verticalCoordinate) {
        this.verticalCoordinate = verticalCoordinate;
    }

    public BufferedImage getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(BufferedImage puzzle) {
        this.puzzle = puzzle;
    }

    public int getInitialHorizontalCoordinate() {
        return initialHorizontalCoordinate;
    }

    public void setInitialHorizontalCoordinate(int initialHorizontalCoordinate) {
        this.initialHorizontalCoordinate = initialHorizontalCoordinate;
    }

    public int getInitialVerticalCoordinate() {
        return initialVerticalCoordinate;
    }

    public void setInitialVerticalCoordinate(int initialVerticalCoordinate) {
        this.initialVerticalCoordinate = initialVerticalCoordinate;
    }

    private void initializeMouseListener() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialHorizontalCoordinate = e.getX();
                initialVerticalCoordinate = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - initialHorizontalCoordinate;
                int deltaY = e.getY() - initialVerticalCoordinate;

                int newX = getX() + deltaX;
                int newY = getY() + deltaY;

                setLocation(newX, newY);
            }
        });
    }
}
