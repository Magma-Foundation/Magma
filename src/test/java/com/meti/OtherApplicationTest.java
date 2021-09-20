package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherApplicationTest {
    public static final Path Source = Paths.get(".", "main");
    public static final Path TargetHeader = Paths.get(".", "main.h");
    public static final Path TargetSource = Paths.get(".", "main.c");

    @Test
    void target_header() throws IOException {
        Files.createFile(TargetHeader);
        new Application(Source).run();
        assertTrue(Files.exists(TargetHeader));
    }

    @Test
    void target_source() throws IOException {
        Files.createFile(TargetSource);
        new Application(Source).run();
        assertTrue(Files.exists(TargetSource));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(TargetHeader);
        Files.deleteIfExists(TargetSource);
    }
}
