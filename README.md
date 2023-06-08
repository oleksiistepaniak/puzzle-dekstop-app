# Desktop PuzzleGame
PuzzleGame is a puzzle game. The project was built based on the principles of SOLID. Your main task is to drag the puzzle pieces on the graphical interface in such a way that they recreate the original image. You can manipulate the puzzle pieces, view the correct solution (by clicking the Solution button), shuffle the puzzle pieces (by clicking the Shuffle button), toggle background music, and view game information.
# Features:

- Manipulate puzzle pieces on the graphical interface using the mouse.
- View the completed solution by clicking the Solution button.
- Shuffle the puzzle pieces on the graphical interface in a random order by clicking the Shuffle button.
- Enable or mute background music during the game by clicking the Enable Music/Mute Music buttons.
- Read basic information about the game by clicking the About Game button.

# Structure:

| Class                    | Brief description                                                                                                                                                                                                                           |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Main                     | It's used to launch the game                                                                                                                                                                                                                |
| BackgroundMusicImpl      | It's used to configure background music and for the functionality of enabling/disabling music on the graphical interface                                                                                                                    |
| BackgroundMusicException | This exception indicates issues with playing the background music                                                                                                                                                                           |
| ImageSplitterException   | This exception indicates issues with cropping the image into puzzle pieces                                                                                                                                                                  |
| PuzzleGameImpl           | It's used to load the game                                                                                                                                                                                                                  |
| PuzzlePiece              | It's used to represent the puzzle model                                                                                                                                                                                                     |
| PuzzleGameFrameImpl      | It's used to create the game's graphical interface and contains certain methods that can be triggered by pressing different buttons. This class also includes a private class used for mouse configuration                                  |
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

# How to run this project locally?
- make sure you have JDK 17 installed on your system;
- clone this repository;
- open this project (you can do this using Intellij IDEA or another development environment);
- run the Main method and enjoy this application;

# Additional information:
If you want to change the background music / image to your own, you can do so in the BackgroundMusicImpl class by modifying the value of the constant representing the path to the music file. Similarly, you can change the image by modifying the constant representing the path to the image file in the PuzzleGameImpl class. If you find any bugs or would like to suggest any improvements, please contact me via email at alexstepanyak@gmail.com.