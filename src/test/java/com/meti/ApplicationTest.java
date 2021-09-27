package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static final Path Root = Paths.get(".");
    private static final Path Source = Root.resolve("index.mgs");
    private static final Path Target = Root.resolve(Application.formatTargetName("index"));

    @Test
    void main() throws IOException {
        runWithSource();
        var output = Files.readString(Target);
        assertEquals("void main(){}", output);
    }

    private void runWithSource() throws IOException {
        Files.createFile(Source);
        run();
    }

    @Test
    void target() throws IOException {
        runWithSource();
        assertTrue(Files.exists(Target));
    }

    private void run() throws IOException {
        new Application(Source).run();
    }

    @Test
    void no_target() throws IOException {
        run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
