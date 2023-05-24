package com.meti.com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static Path source;
    private static Path target;

    private static void run() {
        try {
            runExceptionally();
        } catch (IOException e) {
            fail(e);
        }
    }

    private static void runExceptionally() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
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
    void generates_target() throws IOException {
        Files.createFile(source);
        run();
        assertTrue(Files.exists(target));
    }

    @Test
    void generates_nothing() {
        run();
        assertFalse(Files.exists(target));
    }
}
