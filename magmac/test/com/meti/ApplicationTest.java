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

    public static final String name = "Index";
    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        source = Paths.get(".", name + ".java");
        target = Paths.get(".", name + ".mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }


    @Test
    void generatesTarget() throws IOException {
        Files.createFile(source);
        run();
        assertTrue(Files.exists(target));
    }

    @Test
    void generatesProperTarget() throws IOException {
        Files.createFile(source);
        assertEquals(target, run());
    }

    private Path run() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
            return target;
        } else {
            return null;
        }
    }

    @Test
    void generateNoTarget() throws IOException {
        run();
        assertFalse(Files.exists(target));
    }
}
