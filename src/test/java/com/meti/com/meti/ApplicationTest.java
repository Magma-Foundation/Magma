package com.meti.com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static Path source;
    private static Path target;

    private static Optional<Path> run() {
        try {
            return runExceptionally(new VolatileSingleSource(source));
        } catch (IOException e) {
            fail(e);
            return Optional.empty();
        }
    }

    private static Optional<Path> runExceptionally(VolatileSingleSource source) throws IOException {
        var sources = source.collectSources();
        var targets = new HashSet<Path>();

        for (var o : sources) {
            var target = run(o);
            targets.add(target);
        }

        return targets.stream().findAny();
    }

    private static Path run(Path source) throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = fileName.substring(0, separator);
        var target = source.resolveSibling(fileNameWithoutExtension + ".mgs");
        Files.createFile(target);
        return target;
    }

    private static Optional<Path> runWithSource() {
        try {
            Files.createFile(source);
            return run();
        } catch (IOException e) {
            fail(e);
            return Optional.empty();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @BeforeEach
    void setUp() {
        source = Paths.get(".", "Main.java");
        target = Paths.get(".", "Main.mgs");
    }

    @Test
    void generatesTarget() {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    @Test
    void generatesNothing() {
        run();
        assertFalse(Files.exists(target));
    }

    @Test
    void generatesProperTarget() {
        assertEquals(target, runWithSource().orElseThrow());
    }
}
