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

    private Path source;
    private Path target;

    @BeforeEach
    void setUp() {
        source = Paths.get(".", "Main.java");
        target = Paths.get(".", "Main.mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void generates_target() throws IOException {
        Files.createFile(source);
        run();
        assertTrue(Files.exists(target));
    }

    private void run() {
        try {
            runExceptionally();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runExceptionally() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
        }
    }

    @Test
    void generates_nothing() {
        run();
        assertFalse(Files.exists(target));
    }
}
