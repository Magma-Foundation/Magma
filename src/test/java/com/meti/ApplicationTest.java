package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path SourceDirectoryPath = Paths.get(".", "source");

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(SourceDirectoryPath);
    }

    @Test
    void test() throws IOException {
        if (!Files.exists(SourceDirectoryPath)) {
            Files.createDirectory(SourceDirectoryPath);
        }
        assertTrue(Files.exists(SourceDirectoryPath));
    }
}
