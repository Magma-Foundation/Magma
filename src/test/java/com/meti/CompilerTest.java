package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importName(String name) {
        var actual = new Compiler(Compiler.renderJavaImport(name)).compile();
        assertEquals(Compiler.renderMagmaImport(name, "java.io"), actual);
    }
}