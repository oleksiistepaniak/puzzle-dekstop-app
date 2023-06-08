package puzzle.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {
    private static final String EXISTING_FILE = "src/main/resources/about.txt";
    private static final String NON_EXISTING_FILE = "src/main/resources/non_existing_file.txt";
    private static final String EMPTY_PATH_TO_FILE = "";

    @Test
    public void loadAboutMessageFromFile_ExistingFile_ok() {
        String expectedContent = "This is the about message.";
        String actualContent = FileLoader.loadAboutMessageFromFile(EXISTING_FILE);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void loadAboutMessageFromFile_NonExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> FileLoader
                .loadAboutMessageFromFile(NON_EXISTING_FILE));
    }

    @Test
    public void loadAboutMessageFromFile_NullFilePath_notOk() {
        assertThrows(NullPointerException.class, () -> FileLoader
                .loadAboutMessageFromFile(null));
    }

    @Test
    public void loadAboutMessageFromFile_EmptyFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> FileLoader
                .loadAboutMessageFromFile(EMPTY_PATH_TO_FILE));
    }
}
