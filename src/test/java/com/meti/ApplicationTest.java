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

    private static void assertCompiles(String input, String output) {
        var actual = runWithSource(input);
        assertEquals(output, actual);
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
        assertCompiles("import simple;", "import simple from *;");
    }

    @Test
    void importSubNamespace() {
        assertCompiles("import com.meti.Main;", "import Main from com.meti;");
    }

    @Test
    void importMultiple() {
        assertCompiles("import first;import second;", "import { first, second } from *;");
    }

    @Test
    void importNamespace() {
        assertCompiles("import foo.bar;", "import bar from foo;");
    }

    @Test
    void importAnotherSingle() {
        assertCompiles("import test;", "import test from *;");
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
