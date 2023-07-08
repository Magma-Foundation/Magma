package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private final Path source = Paths.get(".", "Main.java");
    private final Path target = Paths.get(".", "Main.mgs");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    private Path runWithSource() throws IOException {
        Files.createFile(source);
        return run();
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(target, runWithSource());
    }

    private Path run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutSeparator = fileName.substring(0, separator);
            var target = source.resolveSibling(fileNameWithoutSeparator + ".mgs");
            Files.createFile(target);
            return target;
        } else {
            return null;
        }
    }

    @Test
    void generatesNothing() throws IOException {
        run();
        assertFalse(Files.exists(target));
    }
}
