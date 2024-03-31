package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.renderMagmaImport;
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
    @ValueSource(ints = {2, 3})
    void multipleImports(int count) {
        var name = "Test";
        var input = renderJavaImport(name).repeat(count);
        var output = renderMagmaImport(name).repeat(count);
        assertCompile(input, output);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AfterEach", "Test"})
    void simpleImport(String name) {
        assertCompile(renderJavaImport(name), renderMagmaImport(name));
    }
}
