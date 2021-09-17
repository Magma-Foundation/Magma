package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static final Path Source = Paths.get(".", "index.mgs");
    private static final Path Target = Paths.get(".", "index.c");

    @Test
    void declaration() {
        assertRun("const x : I16 = 10;",
            "struct ___index___{};" +
            "struct ___index___ __index__(){" +
            "struct ___index___ this={};int x=10;return this;}");
    }

    private void assertRun(String input, String output) {
        try {
            Files.writeString(Source, input);
            new Application(Source).run();
            assertEquals(Files.readString(Target), output);
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void declaration_name() {
        assertRun("const test : I16 = 10;",
                "struct ___index___{};" +
                "struct ___index___ __index__(){" +
                "struct ___index___ this={};int test=10;return this;}");
    }

    @Test
    void exists() {
        assertRun("", "struct ___index___{};struct ___index___ __index__(){struct ___index___ this={};return this;}");
    }

    @Test
    void not_exists() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
