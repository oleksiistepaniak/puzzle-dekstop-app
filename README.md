# Desktop PuzzleGame
PuzzleGame is a desktop puzzle game. The project was built on the principles of SOLID. Your main task is to assemble the puzzle pieces on the graphical interface in a way that reproduces the original image. You can manipulate the puzzle pieces by clicking on them (imagine that you click on puzzle piece A, it is selected, then you click on puzzle piece B, and a position swap occurs between these puzzle pieces). The correctness of the solution is checked after each move (after each change in the positions of the puzzle pieces).
# Features:

- The "SOLUTION" button allows you to view the complete solution.
- The "SHUFFLE" button allows you to shuffle the puzzle pieces.
- The "MUTE MUSIC" button allows you to turn off the background music.
- The "ENABLE MUSIC" button allows you to turn on the background music.
- The "ABOUT GAME" button provides brief information about the game and the developer.
- The "LOAD IMAGE" button allows you to upload your own image, which will be
  automatically divided into puzzle pieces.
- By clicking on the puzzle pieces, you can change their positions.
- Solution verification is performed after each puzzle piece movement on
  the graphical interface.

# Structure:

| Class                    | Brief description                                                                                                                                                                                                                           |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main                     | It's used to launch the game                                                                                                                                                                                                                |
| BackgroundMusicImpl      | It's used to configure background music and for the functionality of enabling/disabling music on the graphical interface                                                                                                                    |
| BackgroundMusicException | This exception indicates issues with playing the background music                                                                                                                                                                           |
| ImageSplitterException   | This exception indicates issues with cropping the image into puzzle pieces                                                                                                                                                                  |
| PuzzleGameImpl           | It's used to load the game                                                                                                                                                                                                                  |
| PuzzlePiece              | It's used to represent the puzzle model                                                                                                                                                                                                     |
| PuzzleGameFrameImpl      | It's used to create the game's graphical interface and contains certain methods that can be triggered by pressing different buttons. Here, mouse settings are also configured.                                                              |
| ImageSplitterImpl        | It's used for image cropping into puzzle pieces and contains the puzzle pieces that recreate the original image. It implements the algorithm for splitting the image into puzzle pieces and assembling them to reproduce the original image |
| FileLoader               | It's used as a utility class for reading text from a file                                                                                                                                                                                   |
| BackgroundMusicImplTest  | It's used to test the functionality of the BackgroundMusicImpl class                                                                                                                                                                        |
| PuzzleGameImplTest       | It's used to test the functionality of the PuzzleGameImpl class                                                                                                                                                                             |
| ImageSplitterImplTest    | It's used to test the functionality of the ImageSplitterImpl class                                                                                                                                                                          |
| FileLoaderTest           | It's used to test the functionality of the FileLoader class                                                                                                                                                                                 |

# Technologies:
- JDK 17;
- Java AWT;
- Java Swing;
- Java IO;
- Java Sound;
- JUnit 5;
- Lombok 1.18.26;

# How to run this project locally?
- make sure you have JDK 17 installed on your system;
- clone this repository;
- open this project (you can do this using Intellij IDEA or another development environment);
- run the Main method and enjoy this application;

# Additional information:
If you find any bugs or would like to suggest any improvements, please contact me via email at alexstepanyak@gmail.com.