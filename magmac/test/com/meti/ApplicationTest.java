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
        target = Paths.get(".", "Test.mgs");
        source = Paths.get(".", "Test.java");
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    @Test
    void generateEmptyTarget() throws IOException {
        var output = Files.readString(runWithSource().orElseThrow());
        assertTrue(output.isEmpty());
    }

    @Test
    void generatesTargetName() throws IOException {
        var run = runWithSource();
        assertEquals(target, run.orElseThrow());
    }

    private Optional<Path> runWithSource() throws IOException {
        Files.createFile(source);
        return new Application(source).run();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesNoTarget() throws IOException {
        new Application(source).run();
        assertFalse(Files.exists(target));
    }
}
