package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PathSourceTest {
    public static final Path ROOT = Paths.get(".");

    @Test
    void findNamespace() {
        var namespace = new PathSource(ROOT, ROOT
                .resolve("com")
                .resolve("meti")
                .resolve("ApplicationTest.java"))
                .findNamespace();
        assertIterableEquals(List.of("com", "meti"), namespace);
    }
}