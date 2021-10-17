package com.meti.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NIOPathTest {

    private static final Path TestPath = Paths.get(".", "test.txt");

    @Test
    void asAbsolutePath() {
        var expected = TestPath.toAbsolutePath().toString();
        var actual = new NIOPath(TestPath).absolute().toString();
        assertEquals(expected, actual);
    }

    @Test
    void readContentAsString() throws IOException, com.meti.api.IOException {
        var expected = "testing";
        Files.writeString(TestPath, expected);
        var path = new NIOPath(TestPath);
        var actual = path.readContentAsString();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TestPath);
    }

    @Test
    void testToString() {
        var expected = TestPath.toString();
        var actual = new NIOPath(TestPath).toString();
        assertEquals(expected, actual);
    }
}