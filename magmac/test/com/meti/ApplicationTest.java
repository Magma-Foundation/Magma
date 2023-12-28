package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final Path Target = Paths.get(".", "Index.mgs");
    public static final Path Source = Paths.get(".", "Index.java");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }

    private static Path tryRunWithSource() {
        try {
            Files.createFile(Source);
            return tryRun();
        } catch (IOException e) {
            fail(e);
            return null;
        }
    }

    private static Path tryRun() {
        try {
            return run();
        } catch (IOException e) {
            fail(e);
            return null;
        }
    }

    private static Path run() throws IOException {
        if (Files.exists(Source)) {
            Files.createFile(Target);
            return Target;
        } else {
            return null;
        }
    }

    @Test
    void generateTarget() {
        tryRunWithSource();
        assertTrue(Files.exists(Target));
    }

    @Test
    void generatesCorrectTarget() {
        assertEquals(Target, tryRunWithSource());
    }

    @Test
    void generatesNoTarget() {
        tryRun();
        assertFalse(Files.exists(Target));
    }
}
