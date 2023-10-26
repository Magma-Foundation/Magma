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

    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        target = Paths.get(".", "Index.mgs");
        source = Paths.get(".", "Index.java");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
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
    void generatesNoTarget() throws IOException {
        run();
        assertFalse(Files.exists(target));
    }
}
