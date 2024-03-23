package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path SOURCE = Paths.get(".", "ApplicationTest.java");
    public static final Path TARGET = Paths.get(".", "ApplicationTest.mgs");

    private static void run() {
        try {
            if (Files.exists(SOURCE)) {
                var input = Files.readString(SOURCE);
                var output = Compiler.compile(input);

                var fileName = SOURCE.getFileName().toString();
                var separator = fileName.indexOf('.');
                var nameWithoutExtension = fileName.substring(0, separator);
                Files.writeString(SOURCE.resolveSibling(nameWithoutExtension + ".mgs"), output);
            }
        } catch (IOException | CompileException e) {
            fail(e);
        }
    }

    @Test
    void compileImport() throws IOException {
        runWithSource("import org.junit.jupiter.api.AfterEach;");
        var actual = Files.readString(TARGET);
        assertEquals("import { AfterEach } from org.junit.jupiter.api;", actual);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TARGET);
        Files.deleteIfExists(SOURCE);
    }

    @Test
    void generatesNoTarget() {
        run();
        assertFalse(Files.exists(TARGET));
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource("");
        assertTrue(Files.exists(TARGET));
    }

    private static void runWithSource(String csq) throws IOException {
        Files.writeString(SOURCE, csq);
        run();
    }
}
