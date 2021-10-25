package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Source = Paths.get(".", "index.mgf");
    public static final Path Target = Paths.get(".", "index.c");

    @Test
    void no_source() throws IOException {
        var target = runImpl();
        assertFalse(Files.exists(target));
    }

    private Path runImpl() throws IOException {
        return new Application(Source).run();
    }

    @Test
    void target() throws IOException {
        assertEquals(Target, runImpl());
    }

    @Test
    void empty() {
        assertCompile("", "");
    }

    private void assertCompile(String input, String output) {
        try {
            Files.writeString(Source, input);
            runImpl();
            assertEquals(output, Files.readString(Target));
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void content() {
        var input = Application.renderNativeImport();
        var output = Application.renderIncludeDirective();
        assertCompile(input, output);
    }

    @Test
    void with_source() throws IOException {
        Files.createFile(Source);
        runImpl();
        assertTrue(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
