package puzzle.ui.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import puzzle.audio.BackgroundMusic;
import puzzle.exception.ImageSplitterException;
import puzzle.game.PuzzleGame;
import puzzle.game.impl.PuzzleGameImpl;
import puzzle.model.PuzzlePiece;
import puzzle.ui.PuzzleGameFrame;
import puzzle.util.FileLoader;
import puzzle.util.ImageSplitter;

public class PuzzleGameFrameImpl extends JFrame implements PuzzleGameFrame {
    private static final String SOLUTION_BUTTON_TEXT = "SOLUTION";
    private static final String SHUFFLE_BUTTON_TEXT = "SHUFFLE";
    private static final String MUTE_MUSIC_BUTTON_TEXT = "MUTE MUSIC";
    private static final String ENABLE_MUSIC_BUTTON_TEXT = "ENABLE MUSIC";
    private static final String ABOUT_GAME_BUTTON_TEXT = "ABOUT GAME";
    private static final String LOAD_IMAGE_BUTTON_TEXT = "LOAD IMAGE";
    private static final String PUZZLE_GAME_APP_NAME_TEXT
            = "PUZZLE GAME BY OLEKSII STEPANIAK";
    private static final String PATH_TO_ABOUT_GAME_FILE
            = "src/main/resources/about_game.txt";
    private static final String PATH_TO_CONGRATULATIONS_MESSAGE
            = "src/main/resources/congratulations.txt";
    private static final String PATH_TO_INVALID_IMAGE_DIMENSIONS_MESSAGE
            = "src/main/resources/invalid_image_dimensions.txt";
    private static final String PATH_TO_ERROR_LOADING_IMAGE_MESSAGE
            = "src/main/resources/error_loading_image.txt";
    private static final String IMAGE_FILES_DESCRIPTION = "Image Files";
    private static final String[] IMAGE_FILE_EXTENSIONS = {"jpg", "jpeg", "png"};
    private static final int AUTOMATIC_ROWS = 0;
    private final List<PuzzlePiece> puzzlePieces;
    private final List<PuzzlePiece> originalPuzzlePieces;
    private final JPanel puzzlePanel;
    private final ImageSplitter imageSplitter;
    private PuzzlePiece selectedPiece;
    private boolean isPieceSelected;

    public PuzzleGameFrameImpl(List<PuzzlePiece> puzzlePieces,
                               List<PuzzlePiece> originalPuzzlePieces,
                               ImageSplitter imageSplitter,
                               BackgroundMusic backgroundMusic) {
        this.puzzlePieces = puzzlePieces;
        this.originalPuzzlePieces = originalPuzzlePieces;
        this.imageSplitter = imageSplitter;

        setTitle(PUZZLE_GAME_APP_NAME_TEXT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(AUTOMATIC_ROWS,
                (int) Math.sqrt(puzzlePieces.size())));

        displayPuzzlePieces();

        JButton solutionButton = createButton(SOLUTION_BUTTON_TEXT,
                this::solvePuzzle);
        JButton shuffleButton = createButton(SHUFFLE_BUTTON_TEXT,
                this::shufflePuzzle);
        JButton muteMusicButton = createButton(MUTE_MUSIC_BUTTON_TEXT,
                backgroundMusic::muteBackgroundMusic);
        JButton enableMusicButton = createButton(ENABLE_MUSIC_BUTTON_TEXT,
                backgroundMusic::enableBackgroundMusic);
        JButton showAboutGameMessageButton = createButton(ABOUT_GAME_BUTTON_TEXT,
                this::showAboutGameMessage);
        JButton loadImageButton = createButton(LOAD_IMAGE_BUTTON_TEXT,
                this::loadImage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solutionButton);
        buttonPanel.add(shuffleButton);
        buttonPanel.add(muteMusicButton);
        buttonPanel.add(enableMusicButton);
        buttonPanel.add(showAboutGameMessageButton);
        buttonPanel.add(loadImageButton);

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
            JLabel pieceLabel = createPieceLabel(piece);
            puzzlePanel.add(pieceLabel);
        }
        validate();
        repaint();
    }

    @Override
    public void checkCorrectSolution() {
        boolean isCorrect = true;
        for (int i = 0; i < puzzlePieces.size(); i++) {
            PuzzlePiece currentPiece = puzzlePieces.get(i);
            PuzzlePiece originalPiece = originalPuzzlePieces.get(i);
            if (currentPiece != originalPiece) {
                isCorrect = false;
                break;
            }
        }
        if (isCorrect) {
            JOptionPane.showMessageDialog(this,
                    FileLoader.loadMessageFromFile(PATH_TO_CONGRATULATIONS_MESSAGE));
        }
    }

    @Override
    public void solvePuzzle() {
        puzzlePieces.clear();
        puzzlePieces.addAll(originalPuzzlePieces);
        displayPuzzlePieces();
    }

    @Override
    public void shufflePuzzle() {
        Collections.shuffle(puzzlePieces);
        displayPuzzlePieces();
    }

    @Override
    public void showAboutGameMessage() {
        String aboutMessage = FileLoader.loadMessageFromFile(PATH_TO_ABOUT_GAME_FILE);
        JOptionPane.showMessageDialog(this, aboutMessage);
    }

    @Override
    public void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(IMAGE_FILES_DESCRIPTION,
                IMAGE_FILE_EXTENSIONS));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                List<PuzzlePiece> newPuzzlePieces = imageSplitter
                        .splitImage(image, PuzzleGameImpl.NUM_ROWS, PuzzleGameImpl.NUM_COLUMNS);
                if (newPuzzlePieces.size() == puzzlePieces.size()) {
                    puzzlePieces.clear();
                    puzzlePieces.addAll(newPuzzlePieces);
                    originalPuzzlePieces.clear();
                    originalPuzzlePieces.addAll(newPuzzlePieces);
                    displayPuzzlePieces();
                } else {
                    JOptionPane.showMessageDialog(this,
                            FileLoader.loadMessageFromFile(PATH_TO_INVALID_IMAGE_DIMENSIONS_MESSAGE));
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        FileLoader.loadMessageFromFile(PATH_TO_ERROR_LOADING_IMAGE_MESSAGE));
            } catch (ImageSplitterException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private JButton createButton(String buttonText, Runnable action) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> action.run());
        return button;
    }

    private JLabel createPieceLabel(PuzzlePiece piece) {
        JLabel pieceLabel = new JLabel();
        pieceLabel.setIcon(new ImageIcon(piece.getPuzzle()));
        pieceLabel.setOpaque(true);
        pieceLabel.setBackground(Color.WHITE);
        pieceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pieceLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isPieceSelected) {
                    selectedPiece = piece;
                    isPieceSelected = true;
                    pieceLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    swapPieces(selectedPiece, piece);
                    selectedPiece = null;
                    isPieceSelected = false;
                    pieceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    checkCorrectSolution();
                }
            }
        });
        return pieceLabel;
    }

    private void swapPieces(PuzzlePiece pieceA, PuzzlePiece pieceB) {
        int indexA = puzzlePieces.indexOf(pieceA);
        int indexB = puzzlePieces.indexOf(pieceB);
        Collections.swap(puzzlePieces, indexA, indexB);
        displayPuzzlePieces();
    }
}
