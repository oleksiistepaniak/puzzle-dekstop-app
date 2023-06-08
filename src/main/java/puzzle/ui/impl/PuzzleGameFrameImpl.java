package puzzle.ui.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import puzzle.audio.BackgroundMusic;
import puzzle.model.PuzzlePiece;
import puzzle.ui.PuzzleGameFrame;
import puzzle.util.FileLoader;

public class PuzzleGameFrameImpl extends JFrame implements PuzzleGameFrame {
    private static final String SOLUTION_BUTTON_TEXT = "Solution";
    private static final String SHUFFLE_BUTTON_TEXT = "Shuffle";
    private static final String MUTE_MUSIC_BUTTON_TEXT = "Mute music";
    private static final String ENABLE_MUSIC_BUTTON_TEXT = "Enable music";
    private static final String ABOUT_GAME_BUTTON_TEXT = "About game";
    private static final String PUZZLE_GAME_APP_NAME_TEXT = "Puzzle Game";
    private static final String PATH_TO_ABOUT_GAME_FILE = "src/main/resources/about_game.txt";
    private final List<PuzzlePiece> puzzlePieces;
    private final List<PuzzlePiece> originalPuzzlePieces;
    private final JPanel puzzlePanel;

    public PuzzleGameFrameImpl(List<PuzzlePiece> puzzlePieces,
                               List<PuzzlePiece> originalPuzzlePieces,
                               BackgroundMusic backgroundMusic) {
        this.puzzlePieces = puzzlePieces;
        this.originalPuzzlePieces = originalPuzzlePieces;

        setTitle(PUZZLE_GAME_APP_NAME_TEXT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(0, (int) Math.sqrt(puzzlePieces.size())));

        displayPuzzlePieces();

        JButton solutionButton = createButton(SOLUTION_BUTTON_TEXT, this::solvePuzzle);
        JButton shuffleButton = createButton(SHUFFLE_BUTTON_TEXT, this::shufflePuzzle);
        JButton muteMusicButton = createButton(MUTE_MUSIC_BUTTON_TEXT,
                backgroundMusic::muteBackgroundMusic);
        JButton enableMusicButton = createButton(ENABLE_MUSIC_BUTTON_TEXT,
                backgroundMusic::enableBackgroundMusic);
        JButton showAboutGameMessageButton = createButton(ABOUT_GAME_BUTTON_TEXT,
                this::showAboutGameMessage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solutionButton);
        buttonPanel.add(shuffleButton);
        buttonPanel.add(muteMusicButton);
        buttonPanel.add(enableMusicButton);
        buttonPanel.add(showAboutGameMessageButton);

        getContentPane().add(puzzlePanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        backgroundMusic.playBackgroundMusic();
    }

    @Override
    public void displayPuzzlePieces() {
        puzzlePanel.removeAll();

        for (PuzzlePiece piece : puzzlePieces) {
            BufferedImage image = piece.getPuzzle();
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel pieceLabel = new JLabel(imageIcon);
            pieceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pieceLabel.setOpaque(true);

            MouseAdapter mouseAdapter = new PuzzlePieceMouseListener(pieceLabel);
            pieceLabel.addMouseListener(mouseAdapter);
            pieceLabel.addMouseMotionListener(mouseAdapter);

            puzzlePanel.add(pieceLabel);
        }

        puzzlePanel.revalidate();
        puzzlePanel.repaint();
    }

    @Override
    public void solvePuzzle() {
        puzzlePanel.removeAll();

        for (PuzzlePiece piece : originalPuzzlePieces) {
            BufferedImage image = piece.getPuzzle();
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel pieceLabel = new JLabel(imageIcon);
            pieceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pieceLabel.setOpaque(true);

            puzzlePanel.add(pieceLabel);
        }

        puzzlePanel.revalidate();
        puzzlePanel.repaint();
    }

    @Override
    public void shufflePuzzle() {
        Collections.shuffle(puzzlePieces);
        displayPuzzlePieces();
    }

    @Override
    public void showAboutGameMessage() {
        String message = FileLoader.loadAboutMessageFromFile(PATH_TO_ABOUT_GAME_FILE);
        JOptionPane.showMessageDialog(this, message, "About Puzzle Game",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private class PuzzlePieceMouseListener extends MouseAdapter {
        private final JLabel pieceLabel;
        private int pressedX;
        private int pressedY;

        public PuzzlePieceMouseListener(JLabel pieceLabel) {
            this.pieceLabel = pieceLabel;
        }

        public void mousePressed(MouseEvent e) {
            pressedX = e.getX();
            pressedY = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
            int panelWidth = puzzlePanel.getWidth();
            int panelHeight = puzzlePanel.getHeight();
            int labelWidth = pieceLabel.getWidth();
            int labelHeight = pieceLabel.getHeight();

            int newX = pieceLabel.getX() + e.getX() - pressedX;
            int newY = pieceLabel.getY() + e.getY() - pressedY;

            newX = Math.max(0, Math.min(newX, panelWidth - labelWidth));
            newY = Math.max(0, Math.min(newY, panelHeight - labelHeight));

            pieceLabel.setLocation(newX, newY);
            updatePuzzlePieceCoordinates(pieceLabel, newX, newY);
        }

        public void mouseClicked(MouseEvent e) {
            pieceLabel.setLocation(pressedX, pressedY);
            updatePuzzlePieceCoordinates(pieceLabel, pressedX, pressedY);
        }

        public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen() - puzzlePanel.getLocationOnScreen().x - pressedX;
            int y = e.getYOnScreen() - puzzlePanel.getLocationOnScreen().y - pressedY;
            pieceLabel.setLocation(x, y);
            updatePuzzlePieceCoordinates(pieceLabel, x, y);
        }

        private void updatePuzzlePieceCoordinates(JLabel pieceLabel, int x, int y) {
            for (PuzzlePiece piece : puzzlePieces) {
                if (pieceLabel.getIcon().equals(new ImageIcon(piece.getPuzzle()))) {
                    piece.setHorizontalCoordinate(x);
                    piece.setVerticalCoordinate(y);
                    break;
                }
            }
        }
    }
}
