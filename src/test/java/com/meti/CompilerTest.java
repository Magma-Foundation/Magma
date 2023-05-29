package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void twoImports() {
        var compiler = new Compiler(Compiler.renderJavaImport("parent", "Child") +
                                    Compiler.renderJavaImport("pibling", "Cousin"));

        var expected = Compiler.renderMagmaImport("parent", "Child") + "\n" +
                       Compiler.renderMagmaImport("pibling", "Cousin");

        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeImports() {
        var parent = "parent";
        var compiler = new Compiler(Compiler.renderJavaImport(parent, "Child") +
                                    Compiler.renderJavaImport(parent, "Sibling"));

        var expected = Compiler.renderMagmaImport(parent, "Child, Sibling");
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importName(String name) {
        var namespace = "java.io";
        var actual = new Compiler(Compiler.renderJavaImport(namespace, name)).compile();
        assertEquals(Compiler.renderMagmaImport(namespace, name), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"java.io", "java.nio"})
    void importNamespace(String namespace) {
        var name = "Test";
        var actual = new Compiler(Compiler.renderJavaImport(namespace, name)).compile();
        assertEquals(Compiler.renderMagmaImport(namespace, name), actual);
    }
}