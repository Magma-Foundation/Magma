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
    public static final Path SOURCE = Paths.get(".", "ApplicationTest.java");
    public static final Path TARGET = Paths.get(".", "ApplicationTest.mgs");

    private static void run() throws IOException {
        if (Files.exists(SOURCE)) {
            Files.createFile(TARGET);
        }
    }

    @Test
    void generatesSomething() throws IOException {
        Files.createFile(SOURCE);
        run();
        assertTrue(Files.exists(TARGET));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }

    @Test
    void generatesNothing() throws IOException {
        run();
        assertFalse(Files.exists(TARGET));
    }
}
