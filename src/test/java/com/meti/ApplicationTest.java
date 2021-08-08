package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "index.java");
    public static final Path Source = Paths.get(".", "index.ms");

    @Test
    void content() throws IOException {
        Files.createFile(Source);
        run();

        assertEquals("class __index__{}", Files.readString(Target));
    }

    private void run() throws IOException {
        if (Files.exists(Source)) {
            Files.writeString(Target, "class __index__{}");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }

    @Test
    void with_source() throws IOException {
        Files.createFile(Source);
        run();
        assertTrue(Files.exists(Target));
    }

    @Test
    void without_source() throws IOException {
        run();
        assertFalse(Files.exists(Target));
    }
}
