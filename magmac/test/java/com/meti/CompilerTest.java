package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    private static void assertCompile(String input, String expected) {
        var actual = Compiler.compile(input);
        assertEquals(expected, actual);
    }

    private static String renderJavaImport(String child) {
        return Compiler.IMPORT_WITH_PARENT + child + ";";
    }

    @Test
    void noPackage() {
        assertCompile("package test;", "");
    }

    @ParameterizedTest
    @ValueSource(strings = {"AfterEach", "Test"})
    void simpleImport(String name) {
        assertCompile(renderJavaImport(name), Compiler.renderMagmaImport(name));
    }
}
