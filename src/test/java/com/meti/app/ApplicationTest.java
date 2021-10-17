package com.meti.app;

import com.meti.java.JavaPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path Project = Paths.get(".", "project.json");

    @Test
    void exists() throws IOException {
        Files.createFile(Project);
        runAndAssertProjectFileExists();
    }

    private static void runAndAssertProjectFileExists() throws IOException {
        new Application(new JavaPath(Project)).run();
        assertTrue(Files.exists(Project));
    }

    @Test
    void no_exists() throws IOException {
        runAndAssertProjectFileExists();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Project);
    }
}
