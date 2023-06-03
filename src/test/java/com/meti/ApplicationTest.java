package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        source = Paths.get(".", "Main.mgs");
        target = Paths.get(".", "Main.java");
    }

    @Test
    void generatesProperTarget() throws IOException {
        Files.createFile(source);
        assertEquals(target, run(source));
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(source);
        run(source);
        assertTrue(Files.exists(target));
    }

    private Path run(Path source) throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);
            var target = source.resolveSibling(fileNameWithoutExtension + ".java");
            Files.createFile(target);
            return this.target;
        } else {
            return null;
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesNothing() throws IOException {
        run(source);
        assertFalse(Files.exists(target));
    }
}
