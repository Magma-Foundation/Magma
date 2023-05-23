package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    private static Path source;
    private static Path target;

    private static Path resolveFile(String name, String extension) {
        return Paths.get(".", name + "." + extension);
    }

    private static Optional<Path> run(Path source) throws IOException {
        var sources = Set.of(source);
        var list = run(sources);

        return list.stream().findAny();
    }

    private static ArrayList<Path> run(Set<Path> sources) throws IOException {
        var list = new ArrayList<Path>();
        for (var path : sources) {
            if (Files.exists(path)) {
                var fileName = path.getFileName().toString();
                var separator = fileName.indexOf(".");
                var fileNameWithoutExtension = fileName.substring(0, separator);

                var target = resolveFile(fileNameWithoutExtension, "mgs");
                Files.createFile(target);
                list.add(target);
            }
        }

        return list;
    }

    @BeforeEach
    void setUp() {
        source = resolveFile("Main", "java");
        target = resolveFile("Main", "mgs");
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
