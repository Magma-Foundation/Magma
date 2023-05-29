package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void twoImports() {
        var compiler = new Compiler(Compiler.renderJavaImport(List.of("parent".split("\\.")), "Child") +
                                    Compiler.renderJavaImport(List.of("pibling".split("\\.")), "Cousin"));

        var expected = Compiler.renderMagmaImport(List.of("parent".split("\\.")), "Child") + "\n" +
                       Compiler.renderMagmaImport(List.of("pibling".split("\\.")), "Cousin");

        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeCousins() {
        var namespace = List.of("grandparent", "parent");
        var compiler = new Compiler(Compiler.renderJavaImport(namespace, "Child") +
                                    Compiler.renderJavaImport(namespace, "Sibling"));

        var expected = Compiler.renderMagmaImport(namespace, "Child, Sibling");
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeSiblings() {
        var parent = "parent";
        var compiler = new Compiler(Compiler.renderJavaImport(List.of(parent.split("\\.")), "Child") +
                                    Compiler.renderJavaImport(List.of(parent.split("\\.")), "Sibling"));

        var expected = Compiler.renderMagmaImport(List.of(parent.split("\\.")), "Child, Sibling");
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importName(String name) {
        var namespace = "java.io";
        var actual = new Compiler(Compiler.renderJavaImport(List.of(namespace.split("\\.")), name)).compile();
        assertEquals(Compiler.renderMagmaImport(List.of(namespace.split("\\.")), name), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"java.io", "java.nio"})
    void importNamespace(String namespace) {
        var name = "Test";
        var actual = new Compiler(Compiler.renderJavaImport(List.of(namespace.split("\\.")), name)).compile();
        assertEquals(Compiler.renderMagmaImport(List.of(namespace.split("\\.")), name), actual);
    }
}