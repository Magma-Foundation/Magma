package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    public static final Path Source = Paths.get(".", "index.mgf");

    @Test
    void exists() throws IOException {
        Files.createFile(Source);
        runAndAssertSourceExists();
    }

    @Test
    void no_exists() throws IOException {
        runAndAssertSourceExists();
    }

    private void runAndAssertSourceExists() throws IOException {
        if (!Files.exists(Source)) Files.createFile(Source);
        assertTrue(Files.exists(Source));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
    }
}
