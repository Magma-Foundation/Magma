package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "__index__.java");
    public static final Path Source = Paths.get(".", "index.ms");

    @Test
    void content() throws IOException {
        Files.createFile(Source);
        run(Source);

        assertEquals("class __index__{}", Files.readString(Target));
    }

    private static void run(Path source) throws IOException {
        if (Files.exists(source)) {
            var lastName = source.getFileName().toString();
            var separator = lastName.indexOf('.');
            var baseName = lastName.substring(0, separator);
            var target = source.resolveSibling("__%s__.java".formatted(baseName));
            Files.writeString(target, "class __index__{}");
        }
    }

    @Test
    void different_name() throws IOException {
        var otherSource = Paths.get(".", "test.ms");
        var otherTarget = otherSource.resolveSibling("__test__.java");

        Files.createFile(otherSource);
        run(otherSource);
        assertTrue(Files.exists(otherTarget));

        Files.deleteIfExists(otherTarget);
        Files.deleteIfExists(otherSource);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }

    @Test
    void with_source() throws IOException {
        Files.createFile(Source);
        run(Source);
        assertTrue(Files.exists(Target));
    }

    @Test
    void without_source() throws IOException {
        run(Source);
        assertFalse(Files.exists(Target));
    }
}
