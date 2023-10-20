package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTest {

    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        target = Paths.get(".", "Test.mgs");
        source = Paths.get(".", "Test.java");
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(source);
        if (Files.exists(source)) {
            Files.createFile(target);
        }
        Files.delete(target);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesNoTarget() {
        assertFalse(Files.exists(target));
    }
}
