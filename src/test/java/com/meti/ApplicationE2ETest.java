package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class ApplicationE2ETest {
    @Test
    void no_writes_target() {
        Paths.get(".", "index.c");
    }
}
