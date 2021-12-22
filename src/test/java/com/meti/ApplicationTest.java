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
    private static final NIOFile Target = resolve("index.c");
    private static final NIOFile Source = resolve("index.mgf");

    private static NIOFile resolve(String name) {
        return new NIOFile(Root.resolve(name));
    }

    @Test
    void creates_target() throws IOException {
        Source.create();
        run();
        assertTrue(Target.exists());
    }

    @Test
    void does_not_create_target() throws IOException {
        run();
        assertFalse(Target.exists());
    }

    private void run() throws IOException {
        if (Source.exists()) {
            Target.create();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }

    public record NIOFile(Path value) {
        private void create() throws IOException {
            Files.createFile(value);
        }

        public void deleteIfExists() throws IOException {
            Files.deleteIfExists(value);
        }

        private boolean exists() {
            return Files.exists(value);
        }
    }
}
