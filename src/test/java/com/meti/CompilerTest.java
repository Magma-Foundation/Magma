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

        var expected = new MagmaImport(new MagmaImportSegment(List.of("parent".split("\\.")), "Child")).render() + "\n" +
                       new MagmaImport(new MagmaImportSegment(List.of("pibling".split("\\.")), "Cousin")).render();

        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergePiblings() {
        var compiler = new Compiler(Compiler.renderJavaImport(List.of("parent"), "Child") +
                                    Compiler.renderJavaImport(List.of("parent", "sibling"), "Nibling"));

        var name = "Child, " + new MagmaImportSegment(List.of("sibling"), "Nibling").render();
        var expected = new MagmaImport(new MagmaImportSegment(List.of("parent"), name)).render();

        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeCousins() {
        var namespace = List.of("grandparent", "parent");
        var compiler = new Compiler(Compiler.renderJavaImport(namespace, "Child") +
                                    Compiler.renderJavaImport(namespace, "Sibling"));

        var expected = new MagmaImport(new MagmaImportSegment(namespace, "Child, Sibling")).render();
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeSiblings() {
        var parent = "parent";
        var compiler = new Compiler(Compiler.renderJavaImport(List.of(parent.split("\\.")), "Child") +
                                    Compiler.renderJavaImport(List.of(parent.split("\\.")), "Sibling"));

        var expected = new MagmaImport(new MagmaImportSegment(List.of(parent.split("\\.")), "Child, Sibling")).render();
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importName(String name) {
        var namespace = "java.io";
        var actual = new Compiler(Compiler.renderJavaImport(List.of(namespace.split("\\.")), name)).compile();
        assertEquals(new MagmaImport(new MagmaImportSegment(List.of(namespace.split("\\.")), name)).render(), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"java.io", "java.nio"})
    void importNamespace(String namespace) {
        var name = "Test";
        var actual = new Compiler(Compiler.renderJavaImport(List.of(namespace.split("\\.")), name)).compile();
        assertEquals(new MagmaImport(new MagmaImportSegment(List.of(namespace.split("\\.")), name)).render(), actual);
    }
}