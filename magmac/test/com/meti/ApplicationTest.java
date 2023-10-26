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
    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        target = Paths.get(".", "Index.mgs");
        source = Paths.get(".", "Index.java");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void generatesCorrectTarget() throws IOException {
        assertEquals(target, runWithSource().orElseThrow());
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    private Optional<Path> runWithSource() throws IOException {
        Files.createFile(source);
        return new Application(source).run();
    }

    @Test
    void generatesNoTarget() throws IOException {
        new Application(source).run();
        assertFalse(Files.exists(target));
    }

    @Test
    void empty(){

    }
}
