package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path ROOT = Paths.get(".");
    private static final Path SOURCE = ROOT.resolve("index" + ".mgs");

    @Test
    void generated() throws IOException {
        boolean condition = runWithSource();
        assertTrue(condition);
    }

    private boolean runWithSource() throws IOException {
        Files.createFile(SOURCE);
        return runApplication();
    }

    private boolean runApplication() throws IOException {
        return new Application(ApplicationTest.SOURCE).run();
    }

    @Test
    void generated_content() throws IOException {
        runWithSource();
        assertTrue(Files.exists(SOURCE.resolveSibling("index.c")));
    }

    @Test
    void not_generated() throws IOException {
        assertFalse(runApplication());
    }
}
