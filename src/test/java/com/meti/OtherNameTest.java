package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class OtherNameTest {
    private static final Path Source = Paths.get(".", "main.mgs");
    private static final Path Target = Paths.get(".", "main.c");

    @Test
    void exists() throws IOException {
        Files.createFile(Source);
        Application.run(Source);
        assertTrue(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
