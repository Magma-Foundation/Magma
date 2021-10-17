package com.meti.java;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NIOPathsTest {

    @Test
    void root() {
        var expected = Paths.get(".").toAbsolutePath().toString();
        var actual = NIOPaths.Root().absolute().toString();
        assertEquals(expected, actual);
    }
}