package puzzle.exception;

public class ImageSplitterException extends RuntimeException {
    public ImageSplitterException(String message) {
        super(message);
    }

    public ImageSplitterException(String message, Throwable cause) {
        super(message, cause);
    }
}
