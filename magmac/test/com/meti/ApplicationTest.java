package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final Path Target = Paths.get(".", "Index.mgs");
    public static final Path Source = Paths.get(".", "Index.java");

    private static Optional<Path> run() throws IOException {
        if (Files.exists(Source)) {
            Files.createFile(Target);
            return Optional.of(Target);
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Path> runWithSource() throws IOException {
        Files.createFile(Source);
        return run();
    }

    @Test
    void generatesAnyTarget() throws IOException {
        var target = runWithSource().orElseThrow();
        assertEquals(target, Target);
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
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
