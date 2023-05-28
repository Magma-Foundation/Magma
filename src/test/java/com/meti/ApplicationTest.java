package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path source;
    private Path target;

    @BeforeEach
    void setUp() {
        source = Paths.get(".", "Main.java");
        target = Paths.get(".", "Main.mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void generates_target() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    private Optional<Path> run() {
        try {
            return runExceptionally();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Path> runExceptionally() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);
            var target = source.resolveSibling(fileNameWithoutExtension + ".mgs");
            Files.createFile(target);
            return Optional.of(target);
        }
        return Optional.empty();
    }

    @Test
    void generatesProperTarget() {
        assertEquals(target, runWithSource());
    }

    private Path runWithSource() {
        try {
            Files.createFile(source);
            return run().orElseThrow();
        } catch (IOException e) {
            fail(e);
            return null;
        }
    }

    @Test
    void generates_nothing() {
        run();
        assertFalse(Files.exists(target));
    }
}
