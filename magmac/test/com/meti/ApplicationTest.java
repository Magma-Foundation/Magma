package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final Path Target = Paths.get(".", "Index.mgs");
    public static final Path Source = Paths.get(".", "Index.java");

    private static Optional<Path> run() throws IOException {
        if (Files.exists(Source)) {
            var input = Files.readString(Source);
            var output = new Compiler(input).compile();

            Files.writeString(Target, output);
            return Optional.of(Target);
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Path> runWithSource() throws IOException {
        return runWithSource("");
    }

    private static Optional<Path> runWithSource(String input) throws IOException {
        Files.writeString(Source, input);
        return run();
    }

    @Test
    void oneImport() throws IOException {
        var output = Files.readString(runWithSource("import parent.Child;").orElseThrow());
        assertEquals("import { Child } from parent;", output);
    }

    @Test
    void omitPackage() throws IOException {
        assertTrue(Files.readString(runWithSource("package test;").orElseThrow()).isEmpty());
    }

    @Test
    void generatesAnyTarget() throws IOException {
        var target = runWithSource().orElseThrow();
        assertEquals(target, Target);
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }

    @Test
    void generatesNoTarget() throws IOException {
        run();
        assertFalse(Files.exists(Target));
    }
}
