package puzzle.exception;

public class BackgroundMusicException extends RuntimeException {
    public BackgroundMusicException(String message) {
        super(message);
    }

    public BackgroundMusicException(String message, Throwable cause) {
        super(message, cause);
    }
}
