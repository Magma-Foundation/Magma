package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTest {
    public static final Path Source = Paths.get(".", "index.mgs");

    @Test
    void test() {
        assertFalse(Files.exists(Source));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
    }
}
