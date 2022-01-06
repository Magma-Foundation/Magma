package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingleApplicationTest {
    private static final NIOPath Source = NIOPath.Root.resolveChild("index.mg");
    private static final NIOPath Target = NIOPath.Root.resolveChild("index.c");

    @Test
    void creates_target() throws IOException {
        Source.createAsFile();
        new Application(new SingleModule(Source)).run();
        assertTrue(Target.exists());
    }

    @Test
    void does_not_create_target() throws IOException {
        new Application(new SingleModule(Source)).run();
        assertFalse(Target.exists());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.delete();
        Target.delete();
    }
}
