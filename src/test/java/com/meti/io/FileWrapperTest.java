package com.meti.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileWrapperTest {
    private static final PathWrapper TestFile = PathWrapper.Root.resolve("test.c");

    @Test
    void readString() throws IOException {
        assertTrue(TestFile.createAsFile()
                .readString()
                .isEmpty());
    }

    @AfterEach
    void tearDown() throws IOException {
        TestFile.deleteIfExists();
    }

    @Test
    void writeString() throws IOException {
        var expected = "test";
        var actual = TestFile.createAsFile()
                .writeString(expected)
                .readString();
        assertEquals(expected, actual);
    }
}
