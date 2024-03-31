package com.meti;

import com.meti.io.SingleSourceSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path ROOT = Paths.get(".");
    public static final Path TARGET = ROOT.resolve("ApplicationTest.mgs");
    public static final Path SOURCE = ROOT.resolve("ApplicationTest.java");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TARGET);
        Files.deleteIfExists(SOURCE);
    }

    @Test
    void generateSomething() throws IOException {
        Files.createFile(SOURCE);
        Main.run(new SingleSourceSet(SOURCE), Paths.get("."));
        assertTrue(Files.exists(TARGET));
    }

    @Test
    void generatesNothing() throws IOException {
        Main.run(new SingleSourceSet(SOURCE), Paths.get("."));
        assertFalse(Files.exists(TARGET));
    }
}
