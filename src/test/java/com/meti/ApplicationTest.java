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
    private static final String Package = "index";
    private static final Path Source = Root.resolve(Package + ".mgs");
    private static final Path Header = Source.resolveSibling(Package + ".h");
    private static final Path Target = Source.resolveSibling(Package + ".c");

    @Test
    void generated() throws IOException {
        assertTrue(runPresent());
    }

    private boolean runPresent() throws IOException {
        return runWithSource().isPresent();
    }

    private Option<TargetSet> runWithSource() throws IOException {
        Files.createFile(Source);
        return runApplication();
    }

    private Option<TargetSet> runApplication() throws IOException {
        return new Application(ApplicationTest.Source).run();
    }

    @Test
    void generated_header() throws IOException {
        assertEquals(runWithSource().get().getHeader(), Header);
    }

    @Test
    void generated_target() throws IOException {
        assertEquals(runWithSource().get().getTarget(), Target);
    }

    @Test
    void generated_target_header() throws IOException {
        runPresent();
        assertTrue(Files.exists(Header));
    }

    @Test
    void generated_target_source() throws IOException {
        runPresent();
        assertTrue(Files.exists(Target));
    }

    @Test
    void not_generated() throws IOException {
        assertFalse(runApplication().isPresent());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Header);
        Files.deleteIfExists(Source);
    }
}
