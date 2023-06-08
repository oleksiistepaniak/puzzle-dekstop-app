package puzzle.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLoader {
    public static String loadAboutMessageFromFile(String filePath) {
        StringBuilder sb = new StringBuilder();

        try {
            String content = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
            sb.append(content);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file " + filePath, e);
        }

        return sb.toString();
    }
}
