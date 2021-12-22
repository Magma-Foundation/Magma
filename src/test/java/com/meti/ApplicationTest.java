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
    void creates_proper_target() throws IOException {
        Source.create();
        assertTrue(run()
                .filter(value -> value.equals(Target))
                .isPresent());
    }

    private Option<FileWrapper> run() throws IOException {
        if (Source.exists()) return new Some<>(Target.create());
        return new None<>();
    }

    @Test
    void creates_target() throws IOException {
        Source.create();
        assertTrue(run().isPresent());
    }

    @Test
    void does_not_create_target() throws IOException {
        assertFalse(run().isPresent());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }
}
