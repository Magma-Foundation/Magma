package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String SIMPLEST_JAVA = "class Test {public static void main(String[] args){}}";

    private static String compileMagmaToJava(String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return SIMPLEST_JAVA;
        } else {
            throw createUnknownInput(magmaInput);
        }
    }

    private static CompileException createUnknownInput(String magmaInput) {
        return new CompileException("Unknown input: " + magmaInput);
    }

    private static String compileJavaToMagma(String javaInput) throws CompileException {
        if (javaInput.equals(SIMPLEST_JAVA)) {
            return "";
        } else {
            throw createUnknownInput(javaInput);
        }
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> compileMagmaToJava("test"));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @Test
    void smallestMagma() throws CompileException {
        assertEquals("", compileJavaToMagma(compileMagmaToJava("")));
    }

    @Test
    void smallestJava() throws CompileException {
        assertEquals(SIMPLEST_JAVA, compileMagmaToJava(compileJavaToMagma(SIMPLEST_JAVA)));
    }
}
