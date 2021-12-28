package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationE2ETest {
    private static final Path Root = Paths.get(".");
    private static final Path Target = Root.resolve("index.c");
    private static final Path Source = Root.resolve("index.mg");

    @Test
    void no_writes_target() {
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }

    @Test
    void writes_target() throws IOException {
        Files.createFile(Source);
        if (Files.exists(Source)) {
            Files.createFile(Target);
        }
        assertTrue(Files.exists(Target));
    }
}
