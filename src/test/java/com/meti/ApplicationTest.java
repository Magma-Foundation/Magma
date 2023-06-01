package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        target = Paths.get(".", "Main.mgs");
        source = Paths.get(".", "Main.java");
    }

    @Test
    void generatesProperTarget() throws IOException {
        Files.createFile(source);
        var actual = new Application(source).run();
        assertEquals(target, actual.unwrap());
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    @Test
    void generatesEmpty() throws IOException {
        assertTrue(Files.readString(runWithSource().unwrap()).isEmpty());
    }

    @Test
    void generatesImport() throws IOException {
        var target = runWithSource("import java.io.IOException;").unwrap();
        assertEquals("import { IOException} from java.io;", Files.readString(target));
    }

    private Option<Path> runWithSource() throws IOException {
        return runWithSource("");
    }

    private Option<Path> runWithSource(String csq) throws IOException {
        Files.writeString(source, csq);
        return new Application(source).run();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void generatesNothing() {
        assertFalse(Files.exists(target));
    }
}
