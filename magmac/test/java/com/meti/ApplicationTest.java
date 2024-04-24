package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileMagmaToJava(String name) throws CompileException {
        assertEquals(Compiler.renderJavaClass(name), Compiler.run(Compiler.renderMagmaFunction(name), false));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> Compiler.run(Compiler.renderJavaClass(Compiler.TEST_LOWER_SYMBOL).repeat(2), true));
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> Compiler.run(Compiler.renderMagmaFunction(Compiler.TEST_LOWER_SYMBOL).repeat(2), false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileJavaToMagma(String name) throws CompileException {
        assertEquals(Compiler.renderMagmaFunction(name), Compiler.run(Compiler.renderJavaClass(name), true));
    }
}