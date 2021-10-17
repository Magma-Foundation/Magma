package com.meti.app;

import com.meti.java.JavaPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    @Test
    void exists() throws IOException {
        Files.createFile(Main.Project);
        runAndAssertProjectFileExists();
    }

    private static void runAndAssertProjectFileExists() throws IOException {
        new Application(new JavaPath(Main.Project)).run();
        assertTrue(Files.exists(Main.Project));
    }

    @Test
    void no_exists() throws IOException {
        runAndAssertProjectFileExists();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Main.Project);
    }
}
