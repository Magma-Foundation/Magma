package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {

    public static final String JAVA_CLASS = "class Main {}";
    public static final String MAGMA_CLASS = "class def Main() => {}";

    private static String compileJavaToMagma(String input) throws CompileException {
        if (input.equals(JAVA_CLASS)) {
            return MAGMA_CLASS;
        } else {
            throw new CompileException("Unknown input: " + input);
        }
    }

    private static String compileMagmaToJava(String input) throws CompileException {
        if (input.equals(MAGMA_CLASS)) {
            return JAVA_CLASS;
        } else {
            throw new CompileException("Unknown input: " + input);
        }
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> compileMagmaToJava("test"));
    }

    @Test
    void magmaSimple() throws CompileException {
        assertEquals(MAGMA_CLASS, compileJavaToMagma(compileMagmaToJava(MAGMA_CLASS)));
    }

    @Test
    void javaSimple() throws CompileException {
        assertEquals(JAVA_CLASS, compileMagmaToJava(compileJavaToMagma(JAVA_CLASS)));
    }
}
