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
    private static final Path ROOT = Paths.get(".");
    private static final Path Source = ROOT.resolve("index" + ".mgs");
    private static final Path Target = Source.resolveSibling("index.c");

    @Test
    void generated() throws IOException {
        assertTrue(runWithSource());
    }

    @Test
    void generated_content() throws IOException {
        runWithSource();
        assertTrue(Files.exists(Target));
    }

    private boolean runWithSource() throws IOException {
        Files.createFile(Source);
        return runApplication();
    }

    private boolean runApplication() throws IOException {
        return new Application(ApplicationTest.Source).run();
    }

    @Test
    void not_generated() throws IOException {
        assertFalse(runApplication());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
