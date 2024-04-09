package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {

    public static final String TEST_NAMESPACE = "Test";

    private static String renderJavaClass(String name) {
        return "class " + name + " {}";
    }

    private static String compileJavaFromMagma(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) return renderJavaClass(namespace);
        throw new CompileException("Unknown input: " + magmaInput);
    }

    private static String compileMagmaFromJava(String javaInput) throws CompileException {
        if (javaInput.startsWith("class ")) return "";
        throw new CompileException("Unknown input: " + javaInput);
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> compileJavaFromMagma(TEST_NAMESPACE, TEST_NAMESPACE));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> compileMagmaFromJava(""));
    }

    @Test
    void emptyMagma() throws CompileException {
        assertEquals("", compileMagmaFromJava(compileJavaFromMagma(TEST_NAMESPACE, "")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void emptyJava(String name) throws CompileException {
        assertEquals(renderJavaClass(name), compileJavaFromMagma(name, compileMagmaFromJava(renderJavaClass(name))));
    }
}
