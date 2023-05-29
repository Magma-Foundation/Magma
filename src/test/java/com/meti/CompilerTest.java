package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importName(String name) {
        var namespace = "java.io";
        var actual = new Compiler(Compiler.renderJavaImport(name, namespace)).compile();
        assertEquals(Compiler.renderMagmaImport(name, namespace), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"java.io", "java.nio"})
    void importNamespace(String namespace) {
        var name = "Test";
        var actual = new Compiler(Compiler.renderJavaImport(name, namespace)).compile();
        assertEquals(Compiler.renderMagmaImport(name, namespace), actual);
    }
}