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
        assertEquals(target, new Application(new VolatileFileGateway(source)).run().orElseThrow());
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(source);
        new Application(new VolatileFileGateway(source)).run().orElseThrow();
        assertTrue(Files.exists(target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesNothing() throws IOException {
        assertTrue(new Application(new VolatileFileGateway(source)).run().isEmpty());
        assertFalse(Files.exists(target));
    }
}
