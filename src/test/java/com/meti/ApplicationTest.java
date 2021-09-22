package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "index.c");
    public static final Path Source = Paths.get(".", "index.mgs");
    public static final Path Header = Paths.get(".", "index.h");

    @Test
    void target_header() throws IOException {
        runImpl();
        assertTrue(Files.exists(Header));
    }

    @Test
    void target_source() throws IOException {
        runImpl();
        assertTrue(Files.exists(Target));
    }

    private void runImpl() throws IOException {
        Files.createFile(Source);
        new Application(Source).run();
    }

    @Test
    void no_target() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Header);
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
