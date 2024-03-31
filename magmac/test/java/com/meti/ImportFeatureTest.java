package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.renderMagmaImport;
import static com.meti.Compiler.renderMagmaImportForAllChildren;

public class ImportFeatureTest {
    public static final String TEST_PARENT = "org.junit.jupiter.api";
    public static final String TEST_NAME = "Test";

    private static String renderJavaImport(String parent, String child) {
        return renderJavaImport("", parent, child);
    }

    private static String renderJavaImport(String staticString, String parent, String child) {
        return Compiler.IMPORT_KEYWORD + staticString + parent + "." + child + ";";
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleImports(int count) {
        var input = renderJavaImport(TEST_PARENT, TEST_NAME).repeat(count);
        var output = renderMagmaImport(TEST_PARENT, TEST_NAME).repeat(count);
        CompiledTest.assertCompile(input, output);
    }

    @Test
    void testWhitespace() {
        CompiledTest.assertCompile("\t" + renderJavaImport(TEST_PARENT, TEST_NAME) + "\t", renderMagmaImport(TEST_PARENT, TEST_NAME));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AfterEach", "Test"})
    void testSimpleImports(String name) {
        CompiledTest.assertCompile(renderJavaImport(TEST_PARENT, name), renderMagmaImport(TEST_PARENT, name));
    }

    @Test
    void testImportsWithParent() {
        var otherParent = "Parent";
        CompiledTest.assertCompile(renderJavaImport(otherParent, TEST_NAME), renderMagmaImport(otherParent, TEST_NAME));
    }

    @Test
    void testImportsForAll() {
        CompiledTest.assertCompile(renderJavaImport(Compiler.STATIC_KEYWORD, ImportFeatureTest.TEST_PARENT, "*"), renderMagmaImportForAllChildren(TEST_PARENT));
    }
}
