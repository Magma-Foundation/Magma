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
    private static final Path Root = Paths.get(".");
    private static final String Package = "index";
    private static final Path Source = Root.resolve(Package + ".mgs");
    private static final Path Target = Source.resolveSibling(Package + ".c");

    @Test
    void generated() throws IOException {
        assertTrue(runWithSource());
    }

    private boolean runWithSource() throws IOException {
        Files.createFile(Source);
        return runApplication();
    }

    private boolean runApplication() throws IOException {
        return new Application(ApplicationTest.Source).run();
    }

    @Test
    void generated_content() throws IOException {
        runWithSource();
        assertTrue(Files.exists(Target));
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
