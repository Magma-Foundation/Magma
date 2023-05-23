package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    private static Path source;
    private static Path target;

    private static Path resolveFile(String extension) {
        return Paths.get(".", "Main." + extension);
    }

    private static Optional<Path> run(Path source) throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
            return Optional.of(target);
        } else {
            return Optional.empty();
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
        run(source);
        assertTrue(Files.exists(target));
    }

    @Test
    void generates_proper_target() throws IOException {
        Files.createFile(source);
        var result = run(source);
        assertTrue(result.isPresent());
    }

    @Test
    void generates_nothing() throws IOException {
        run(source);
        assertFalse(Files.exists(target));
    }
}
