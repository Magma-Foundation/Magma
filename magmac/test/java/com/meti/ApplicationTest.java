package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final String TEST_UPPER_SYMBOL = "Test";

    private static void assertCompileMagma(String name) throws CompileException {
        assertEquals(renderJavaClass(name), run(renderMagmaClass(name), false));
    }

    private static void assertJavaCompile(String input, String output) {
        try {
            assertEquals(output, run(input, true));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void compileJavaFunction() {
        assertJavaCompile(renderJavaClass(TEST_UPPER_SYMBOL, renderJavaMethod()),
                renderMagmaClass(TEST_UPPER_SYMBOL, renderMagmaFunction("", "empty", "")));
    }



    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileMagmaToJava(String name) throws CompileException {
        assertCompileMagma(name);
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> run(renderJavaClass(TEST_LOWER_SYMBOL).repeat(2), true));
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> run(renderMagmaClass(TEST_LOWER_SYMBOL).repeat(2), false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileJavaToMagma(String name) throws CompileException {
        assertJavaCompile(renderJavaClass(name), renderMagmaClass(name));
    }
}