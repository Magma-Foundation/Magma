package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherApplicationTest {
    public static final Path Target = Paths.get(".", "main.c");
    public static final Path Source = Paths.get(".", "main.mgs");

    @Test
    void target() throws IOException {
        Files.createFile(Source);
        new Application(Source).run();
        assertTrue(Files.exists(Target));
    }

    @Test
    void no_target() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
