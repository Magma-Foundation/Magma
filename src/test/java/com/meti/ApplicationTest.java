package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static Path source;
    private static Path target;

    private static Optional<Path> run(Path source) {
        try {
            var sources = Set.of(source);
            var list = new Application(sources).run();
            return list.stream().findAny();
        } catch (IOException e) {
            fail(e);
            return Optional.empty();
        }
    }

    private static String runWithSource(String input) {
        try {
            Files.writeString(source, input);
            var output = run(source).orElseThrow();
            return Files.readString(output);
        } catch (IOException e) {
            fail(e);
            return "";
        }
    }

    @BeforeEach
    void setUp() {
        source = Application.resolveFile("Main", "java");
        target = Application.resolveFile("Main", "mgs");
    }

    @Test
    void compilesPackage() {
        assertTrue(runWithSource("package com.meti;").isEmpty());
    }

    @Test
    void importSingle() {
        var actual = runWithSource("import simple;");
        assertEquals("import simple from '*';", actual);
    }

    @Test
    void importMultiple() {
        var actual = runWithSource("import first;import second;");
        assertEquals("import { first, second } from '*';", actual);
    }

    @Test
    void importAnotherSingle() {
        var actual = runWithSource("import test;");
        assertEquals("import test from '*';", actual);
    }


    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generates_target() throws IOException {
        Files.createFile(source);
        run(source);
        assertTrue(Files.exists(target));
    }

    @Test
    void generates_proper_target() throws IOException {
        Files.createFile(source);
        var result = run(source);
        assertTrue(result.isPresent());
    }

    @Test
    void generates_nothing() throws IOException {
        run(source);
        assertFalse(Files.exists(target));
    }
}
