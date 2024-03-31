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
    public static final Path ROOT = Paths.get(".");
    public static final Path TARGET = ROOT.resolve("ApplicationTest.mgs");
    public static final Path SOURCE = ROOT.resolve("ApplicationTest.java");

    private static void run() throws IOException {
        if (Files.exists(SOURCE)) {
            var fileName = SOURCE.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);
            Files.createFile(SOURCE.resolveSibling(fileNameWithoutExtension + ".mgs"));
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TARGET);
        Files.deleteIfExists(SOURCE);
    }

    @Test
    void generateSomething() throws IOException {
        Files.createFile(SOURCE);
        run();
        assertTrue(Files.exists(TARGET));
    }

    @Test
    void generatesNothing() throws IOException {
        run();
        assertFalse(Files.exists(TARGET));
    }
}
