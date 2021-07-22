package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApplicationTest {
    @Test
    void test() throws IOException {
        var path = Paths.get(".", "src/test/java-gen/com/meti/ApplicationTest.java");
        if (!Files.exists(path)) Files.createDirectories(path.getParent());
        if (!Files.exists(path)) Files.createFile(path);
        Assertions.assertTrue(Files.exists(path));
    }
}
