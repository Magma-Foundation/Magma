package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final Path TARGET = Paths.get(".", "Index.mgs");
    public static final Path SOURCE = Paths.get(".", "Index.java");

    private static Path run() throws IOException {
        if (Files.exists(SOURCE)) {
            var fileName = SOURCE.getFileName().toString();
            var index = fileName.indexOf(".");
            var name = fileName.substring(0, index);
            var target = SOURCE.resolveSibling(name + ".mgs");
            Files.createFile(target);
            return target;
        } else {
            return null;
        }
    }

    private static Path runWithSource() throws IOException {
        Files.createFile(SOURCE);
        return run();
    }

    @Test
    void generatesNothing() throws IOException {
        run();
        assertFalse(Files.exists(TARGET));
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(runWithSource(), TARGET);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }

    @Test
    void generatesSomething() throws IOException {
        runWithSource();
        assertTrue(Files.exists(TARGET));
    }
}
