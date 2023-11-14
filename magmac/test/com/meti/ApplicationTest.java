package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final String name = "Index";
    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        source = Paths.get(".", name + ".java");
        target = Paths.get(".", name + ".mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void package_() throws IOException {
        assertTrue(Files.readString(runWithSource().orElseThrow()).isEmpty());
    }

    @Test
    void import_() throws IOException {
        assertEquals("import { Bar } from foo;", Files.readString(runWithSource("import foo.Bar").orElseThrow()));
    }

    @Test
    void empty() throws IOException {
        runWithSource();
        assertTrue(Files.readString(target).isEmpty());
    }

    private Optional<Path> runWithSource() throws IOException {
        return runWithSource("");
    }

    private Optional<Path> runWithSource(String input) throws IOException {
        Files.writeString(source, input);
        return createApplication().run();
    }

    private Application createApplication() {
        return new Application(new SingleSource(source), new Target(Paths.get(".")));
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(target, runWithSource().orElseThrow());
    }

    @Test
    void generateNoTarget() throws IOException {
        createApplication().run();
        assertFalse(Files.exists(target));
    }
}
