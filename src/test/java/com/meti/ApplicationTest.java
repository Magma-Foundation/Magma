package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static Path source;
    private static Path target;

    private static Optional<Path> run(Path source) throws IOException {
        var sources = Set.of(source);
        var list = new Application(sources).run();
        return list.stream().findAny();
    }

    @BeforeEach
    void setUp() {
        source = Application.resolveFile("Main", "java");
        target = Application.resolveFile("Main", "mgs");
    }

    @Test
    void compilesPackage() throws IOException {
        Files.writeString(source, "package com.meti;");
        assertTrue(Files.readString(run(source).orElseThrow()).isEmpty());
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
