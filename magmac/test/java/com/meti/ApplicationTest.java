package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String TEST_NAME = "Test";
    public static final String CLASS_KEYWORD = "class ";
    public static final String CLASS_SUFFIX = " {public static void main(String[] args){}}";

    private static String renderJavaClass(String name) {
        return CLASS_KEYWORD + name + CLASS_SUFFIX;
    }

    private static String compileMagmaToJava(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return renderJavaClass(namespace);
        } else {
            throw createUnknownInput(magmaInput);
        }
    }

    private static CompileException createUnknownInput(String magmaInput) {
        return new CompileException("Unknown input: " + magmaInput);
    }

    private static String compileJavaToMagma(String javaInput) throws CompileException {
        if (javaInput.startsWith(CLASS_KEYWORD)) {
            return "";
        } else {
            throw createUnknownInput(javaInput);
        }
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(TEST_NAME, "test"));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @Test
    void smallestMagma() throws CompileException {
        assertEquals("", compileJavaToMagma(compileMagmaToJava(TEST_NAME, "")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void smallestJava(String className) throws CompileException {
        assertEquals(renderJavaClass(className), compileMagmaToJava(className, compileJavaToMagma(renderJavaClass(className))));
    }
}
