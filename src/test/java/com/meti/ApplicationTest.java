package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.FileWrapper.Root;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final FileWrapper Source = Root.resolve("index.mgf");
    private static final FileWrapper Target = Root.resolve("index.c");

    @Test
    void creates_proper_target() throws IOException {
        Source.create();
        assertTrue(run()
                .filter(value -> value.equals(Target))
                .isPresent());
    }

    @Test
    void creates_target() throws IOException {
        Source.create();
        assertTrue(doesCreateTarget());
    }

    private boolean doesCreateTarget() throws IOException {
        return run().isPresent();
    }

    private Option<FileWrapper> run() throws IOException {
        return new Application(Source).run();
    }

    @Test
    void does_not_create_target() throws IOException {
        assertFalse(doesCreateTarget());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }
}
