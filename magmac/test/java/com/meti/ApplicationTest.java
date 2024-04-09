package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    private static String renderJavaMethod(String name) {
        return "class " + name + " {}";
    }

    private static String compileMagmaFromJava(String input) throws CompileException {
        if (input.isEmpty()) return renderJavaMethod("Test");
        throw new CompileException("Unknown input: " + input);
    }

    private static String compileJavaFromMagma(String input) throws CompileException {
        if (input.equals(renderJavaMethod("Test"))) return "";
        throw new CompileException("Unknown input: " + input);
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> compileMagmaFromJava("Test"));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> compileJavaFromMagma(""));
    }

    @Test
    void emptyMagma() throws CompileException {
        assertEquals("", compileJavaFromMagma(compileMagmaFromJava("")));
    }

    @Test
    void emptyJava() throws CompileException {
        assertEquals(renderJavaMethod("Test"), compileMagmaFromJava(compileJavaFromMagma(renderJavaMethod("Test"))));
    }
}
