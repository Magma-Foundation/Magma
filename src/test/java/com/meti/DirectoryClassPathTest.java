package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryClassPathTest {
    public static final Path TestFile = Paths.get(".", "Test.mgf");

    @Test
    void isDefined() {
        assertTrue(new DirectoryClassPath(Paths.get(".")).isDefined("Test"));
    }

    @BeforeEach
    void setUp() throws IOException {
        if (!Files.exists(TestFile)) {
            Files.createFile(TestFile);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TestFile);
    }
}