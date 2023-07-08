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

    private final Path source = Paths.get(".", "Main.java");
    private final Path target = Paths.get(".", "Main.mgs");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(source);
        run();
        assertTrue(Files.exists(target));
    }

    private void run() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
        }
    }

    @Test
    void generatesNothing() throws IOException {
        run();
        assertFalse(Files.exists(target));
    }
}
