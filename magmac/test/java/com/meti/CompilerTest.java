package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.renderMagmaImport;
import static com.meti.Compiler.renderMagmaImportForAllChildren;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    public static final String PARENT = "org.junit.jupiter.api";
    public static final String TEST_NAME = "Test";

    private static void assertCompile(String input, String expected) {
        var actual = Compiler.compile(input);
        assertEquals(expected, actual);
    }

    private static String renderJavaImport(String parent, String child) {
        return renderJavaImport("", parent, child);
    }

    private static String renderJavaImport(String staticString, String parent, String child) {
        return Compiler.IMPORT_KEYWORD + staticString + parent + "." + child + ";";
    }

    @Test
    void noPackage() {
        assertCompile("package test;", "");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void multipleImports(int count) {
        var input = renderJavaImport(PARENT, TEST_NAME).repeat(count);
        var output = renderMagmaImport(PARENT, TEST_NAME).repeat(count);
        assertCompile(input, output);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AfterEach", "Test"})
    void simpleImport(String name) {
        assertCompile(renderJavaImport(PARENT, name), renderMagmaImport(PARENT, name));
    }

    @Test
    void parentImport() {
        var otherParent = "Parent";
        assertCompile(renderJavaImport(otherParent, TEST_NAME), renderMagmaImport(otherParent, TEST_NAME));
    }

    @Test
    void allImport() {
        assertCompile(renderJavaImport(Compiler.STATIC_KEYWORD, CompilerTest.PARENT, "*"), renderMagmaImportForAllChildren(PARENT));
    }
}
