package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTest {
    @Test
    void generatesNothing() {
        assertFalse(Files.exists(Paths.get(".", "Main.java")));
    }
}
