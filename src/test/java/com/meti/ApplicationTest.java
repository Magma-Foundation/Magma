package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "index.c");
    public static final Path Source = Paths.get(".", "index.mgs");
    public static final Path Header = Paths.get(".", "index.h");

    @Test
    void target_header() throws IOException {
        runEmpty();
        assertTrue(Files.exists(Header));
    }

    @Test
    void target_source() throws IOException {
        runEmpty();
        assertTrue(Files.exists(Target));
    }

    private void runEmpty() throws IOException {
        run("");
    }

    private void run(String input) throws IOException {
        Files.writeString(Source, input);
        try {
            new Application(Source).run();
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    @Test
    void no_target() throws IOException {
        try {
            new Application(Source).run();
        } catch (ApplicationException e) {
            fail(e);
        }
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Header);
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
