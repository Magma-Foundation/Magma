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

    private static final Path Root = Paths.get(".");
    private static final Path SourceDirectory = Root.resolve("source");
    private static final Path OutDirectory = Root.resolve("out");

    @Test
    void creates_out_directory() throws IOException {
        Files.createDirectory(SourceDirectory);
        if (Files.exists(SourceDirectory)) {
            Files.createDirectory(OutDirectory);
        }

        assertTrue(Files.exists(OutDirectory));
    }

    @Test
    void does_not_create_out_directory() {
        assertFalse(Files.exists(OutDirectory));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(OutDirectory);
        Files.deleteIfExists(SourceDirectory);
    }
}
