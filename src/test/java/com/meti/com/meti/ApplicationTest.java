package com.meti.com.meti;

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
    private static Path source;
    private static Path target;

    private static Optional<Path> run() {
        try {
            return new Application(new VolatileSingleSource(source)).runExceptionally();
        } catch (IOException e) {
            fail(e);
            return Optional.empty();
        }
    }

    private static Optional<Path> runWithSource() {
        try {
            Files.createFile(source);
            return run();
        } catch (IOException e) {
            fail(e);
            return Optional.empty();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @BeforeEach
    void setUp() {
        source = Paths.get(".", "Main.java");
        target = Paths.get(".", "Main.mgs");
    }

    @Test
    void generatesTarget() {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    @Test
    void generatesNothing() {
        run();
        assertFalse(Files.exists(target));
    }

    @Test
    void generatesProperTarget() {
        assertEquals(target, runWithSource().orElseThrow());
    }
}
