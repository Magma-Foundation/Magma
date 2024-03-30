package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    public static final Path ROOT = Paths.get(".");
    public static final Path TARGET = ROOT.resolve("ApplicationTest.mgs");
    public static final Path SOURCE = ROOT.resolve("ApplicationTest.java");

    private static void run(SourceSet sourceSet) throws IOException {
        var set = sourceSet.collect();

        for (var path : set) {
            var name = path.getFileName().toString();
            var separator = name.indexOf('.');
            var withoutExtension = name.substring(0, separator);
            Files.createFile(path.resolveSibling(withoutExtension + ".mgs"));
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TARGET);
        Files.deleteIfExists(SOURCE);
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(SOURCE);
        run(new SingleSourceSet(SOURCE));
        assertTrue(Files.exists(TARGET));
    }

    @Test
    void test() throws IOException {
        run(new SingleSourceSet(SOURCE));
        assertFalse(Files.exists(TARGET));
    }
}
