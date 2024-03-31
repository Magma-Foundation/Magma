package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    private static void assertCompile(String input, String expected) {
        var actual = Compiler.compile(input);
        assertEquals(expected, actual);
    }

    @Test
    void noPackage() {
        assertCompile("package test;", "");
    }

    @Test
    void simpleImport() {
        assertCompile("import org.junit.jupiter.api.AfterEach;", "import { AfterEach } from org.junit.jupiter.api;");
    }

    @Test
    void importChild() {
        assertCompile("import org.junit.jupiter.api.Test;", "import { Test } from org.junit.jupiter.api;");
    }
}
