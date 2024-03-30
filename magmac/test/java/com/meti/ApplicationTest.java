package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {
    private static void assertInvalid(String input) {
        assertInvalid(input, Collections.singletonList("test"));
    }

    private static void assertInvalid(String input, List<String> namespace) {
        assertThrows(CompileException.class, () -> new Unit(namespace)
                .compile(input));
    }

    @Test
    void noMultiplePackage() {
        assertInvalid("package test;package test;");
    }

    @Test
    void simple() {
        assertValid("import test;", "extern import test;");
    }

    private static void assertValid(String input, String output) {
        try {
            assertEquals(output, new Unit().compile(input));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void name() {
        assertValid("import first;", "extern import first;");
    }

    @Test
    void repeat() {
        assertInvalid("import test;import test;");
    }
}