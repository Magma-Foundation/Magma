package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest {
    @Test
    void noMultiplePackage() {
        assertThrows(CompileException.class, () -> new Unit(Collections.singletonList("test"))
                .compile("package test;package test;"));
    }

    @Test
    void importTest() throws CompileException {
        var actual = new Unit().compile("import test;");
        assertEquals("import test;", actual);
    }
}