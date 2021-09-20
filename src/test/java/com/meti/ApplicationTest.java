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
    public static final Path Source = Paths.get(".", "index.mgs");
    public static final Path TargetHeader = Paths.get(".", "index.h");
    public static final Path TargetSource = Paths.get(".", "index.c");

    @Test
    void target_header_present() throws IOException {
        Files.createFile(Source);
        run();
        assertTrue(Files.exists(TargetHeader));
    }

    @Test
    void target_source_present() throws IOException {
        Files.createFile(Source);
        run();
        assertTrue(Files.exists(TargetSource));
    }

    private void run() throws IOException {
        if (Files.exists(Source)) {
            Files.createFile(TargetHeader);
            Files.createFile(TargetSource);
        }
    }

    @Test
    void target_header_missing() throws IOException {
        run();
        assertFalse(Files.exists(TargetHeader));
    }

    @Test
    void target_source_missing() throws IOException {
        run();
        assertFalse(Files.exists(TargetSource));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(TargetHeader);
        Files.deleteIfExists(TargetSource);
    }
}
