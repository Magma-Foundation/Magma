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
    public static final Path Source = Paths.get(".", "index.mgf");
    public static final Path Target = Paths.get(".", "index.c");

    @Test
    void no_source() throws IOException {
        var target = runImpl();
        assertFalse(Files.exists(target));
    }

    private Path runImpl() throws IOException {
        return new Application(Source).run();
    }

    @Test
    void with_source() throws IOException {
        var target = runImpl();
        assertTrue(Files.exists(target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
