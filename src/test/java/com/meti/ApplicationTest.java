package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.FileWrapper.Root;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final FileWrapper Target = Root.resolve("index.c");
    private static final FileWrapper Source = Root.resolve("index.mgf");

    @Test
    void creates_target() throws IOException {
        Source.create();
        run();
        assertTrue(Target.exists());
    }

    private void run() throws IOException {
        if (Source.exists()) {
            Target.create();
        }
    }

    @Test
    void does_not_create_target() throws IOException {
        run();
        assertFalse(Target.exists());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }
}
