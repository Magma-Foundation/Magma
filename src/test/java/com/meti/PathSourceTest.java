package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathSourceTest {

    @Test
    void findPackage() {
        var root = Paths.get(".");
        var value = root.resolve("com")
                .resolve("meti")
                .resolve("Main.mgs");

        var expected = new ListPackage(List.of("com", "meti"), "Main");
        assertEquals(expected, new PathSource(root, value).findPackage());
    }
}