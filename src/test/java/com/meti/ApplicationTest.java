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
        Application application = new Application(source);
        return Results.unwrap(application.run1()).unwrapOrPanic();
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(target, runWithSource());
    }

    @Test
    void generatesNothing() throws IOException {
        Application application = new Application(source);
        Results.unwrap(application.run1());
        assertFalse(Files.exists(target));
    }
}
