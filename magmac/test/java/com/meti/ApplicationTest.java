package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    @Test
    void empty() {
        assertThrows(CompileException.class, ApplicationTest::run);
    }

    private static void run() throws CompileException {
        throw new CompileException("No class present.");
    }
}
