package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherApplicationTest {
    private static final Path Root = Paths.get(".");
    private static final Path Source = Root.resolve("main.mgs");
    private static final Path Target = Root.resolve(Application.formatTargetName("main"));

    @Test
    void target() throws IOException {
        Files.createFile(Source);
        runImpl();
        assertTrue(Files.exists(Target));
    }

    private void runImpl() throws IOException {
        new Application(Source).run();
    }

    @Test
    void no_target() throws IOException {
        runImpl();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
