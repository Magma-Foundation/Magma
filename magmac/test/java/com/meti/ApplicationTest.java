package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void simple() throws CompileException {
        var actual = new Unit().compile("import test;");
        assertEquals("import test;", actual);
    }

    @Test
    void repeat() {
        assertInvalid("import test;import test;");
    }


}