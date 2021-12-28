package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationE2ETest {
    private static final NIOPath Target = Root.resolve("index.c");
    private static final NIOPath Source = Root.resolve("index.mg");

    @Test
    void no_writes_target() throws IOException {
        run();
        assertFalse(Target.exists());
    }

    private static void run() throws IOException {
        if (Source.exists()) {
            Target.create();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }

    @Test
    void writes_target() throws IOException {
        Source.create();
        run();
        assertTrue(Target.exists());
    }
}
