package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    private static Path source;
    private static Path target;

    private static Path resolveFile(String extension) {
        return Paths.get(".", "Main." + extension);
    }

    private static void run() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
        }
    }

    @BeforeEach
    void setUp() {
        source = resolveFile("java");
        target = resolveFile("mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generates_target() throws IOException {
        Files.createFile(source);
        run();
        assertTrue(Files.exists(target));
    }

    @Test
    void generates_nothing() throws IOException {
        run();
        assertFalse(Files.exists(target));
    }
}
