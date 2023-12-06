package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    public static final Path Target = Paths.get(".", "Index.mgs");
    public static final Path Source = Paths.get(".", "Index.java");

    private static void run() throws IOException {
        if (Files.exists(Source)) {
            Files.createFile(Target);
        }
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(Source);
        run();
        assertTrue(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }

    @Test
    void generatesNoTarget() throws IOException {
        run();
        assertFalse(Files.exists(Target));
    }
}
