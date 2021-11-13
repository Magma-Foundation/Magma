package com.meti.app;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static final java.nio.file.Path Source = Paths.get(".", "index.mgs");
    private static final java.nio.file.Path Target = Paths.get(".", "index.c");

    @Test
    void no_target() {
        assertFalse(isTargetCreated());
    }

    private boolean isTargetCreated() {
        return runImpl().isPresent();
    }

    private Option<? extends Path> runImpl() {
        try {
            return new Application(new NIOPath(Source)).run();
        } catch (ApplicationException e) {
            fail(e);
            return new None<>();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }

    @Test
    void with_target() throws IOException {
        Files.createFile(Source);
        assertTrue(isTargetCreated());
    }
}
