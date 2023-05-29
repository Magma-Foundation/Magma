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

        var expected = new MagmaImport(MagmaImportSegment.fromChildren(List.of("parent".split("\\.")), List.of("Child".split(", ")))).render() + "\n" +
                       new MagmaImport(MagmaImportSegment.fromChildren(List.of("pibling".split("\\.")), List.of("Cousin".split(", ")))).render();

        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    /*
    import {
        Child,
        { Nibling } from sibling
    } from parent;
     */
    @Test
    void mergePiblings() {
        var compiler = new Compiler(Compiler.renderJavaImport(List.of("parent"), "Child") +
                                    Compiler.renderJavaImport(List.of("parent", "sibling"), "Nibling"));

        var name = "Child, " + MagmaImportSegment.fromChildren(List.of("sibling"), List.of("Nibling")).render();
        var expected = new MagmaImport(MagmaImportSegment.fromChildren(List.of("parent"), List.of(name))).render();

        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeCousins() {
        var namespace = List.of("grandparent", "parent");
        var compiler = new Compiler(Compiler.renderJavaImport(namespace, "Child") +
                                    Compiler.renderJavaImport(namespace, "Sibling"));

        var expected = new MagmaImport(MagmaImportSegment.fromChildren(namespace, List.of("Child, Sibling".split(", ")))).render();
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @Test
    void mergeSiblings() {
        var parent = "parent";
        var compiler = new Compiler(Compiler.renderJavaImport(List.of(parent.split("\\.")), "Child") +
                                    Compiler.renderJavaImport(List.of(parent.split("\\.")), "Sibling"));

        var expected = new MagmaImport(MagmaImportSegment.fromChildren(List.of(parent.split("\\.")), List.of("Child, Sibling".split(", ")))).render();
        var actual = compiler.compile();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importName(String name) {
        var namespace = "java.io";
        var actual = new Compiler(Compiler.renderJavaImport(List.of(namespace.split("\\.")), name)).compile();
        assertEquals(new MagmaImport(MagmaImportSegment.fromChildren(List.of(namespace.split("\\.")), List.of(name.split(", ")))).render(), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"java.io", "java.nio"})
    void importNamespace(String namespace) {
        var name = "Test";
        var actual = new Compiler(Compiler.renderJavaImport(List.of(namespace.split("\\.")), name)).compile();
        assertEquals(new MagmaImport(MagmaImportSegment.fromChildren(List.of(namespace.split("\\.")), List.of(name.split(", ")))).render(), actual);
    }
}