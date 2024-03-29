package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest {
    @Test
    void noMultiplePackage() {
        assertThrows(CompileException.class, () -> Application.compile("package test;package test;",
                Collections.singletonList("test")));
    }
}