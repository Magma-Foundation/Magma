package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "index.java");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
    }

    @Test
    void test() throws IOException {
        Files.writeString(Target, "class index{}");
        Assertions.assertEquals("class index{}", Files.readString(Target));
    }
}
