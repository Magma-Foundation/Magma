package com.meti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTest {

    private Path target;

    @BeforeEach
    void setUp() {
        target = Paths.get(".", "Test.mgs");
    }

    @Test
    void generatesNoTarget() {
        assertFalse(Files.exists(target));
    }
}
