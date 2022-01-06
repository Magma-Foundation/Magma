package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path Source = NIOPath.Root.resolveChild("index.mg");
    private static final Path Target = NIOPath.Root.resolveChild("index.c");

    @Test
    void creates_target() throws IOException {
        Files.createFile(Source);
        new Application(Source).run();
        assertTrue(Files.exists(Target));
    }

    @Test
    void does_not_create_target() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }
}
