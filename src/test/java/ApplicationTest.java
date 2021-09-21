import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "index.c");
    public static final Path Source = Paths.get(".", "index.mgs");

    @Test
    void target() throws IOException {
        Files.createFile(Source);
        run();
        assertTrue(Files.exists(Target));
    }

    private void run() throws IOException {
        if (Files.exists(Source)) {
            Files.createFile(Target);
        }
    }

    @Test
    void no_target() throws IOException {
        run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
