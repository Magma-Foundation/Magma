package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String TEST_NAME = "Test";
    public static final String CLASS_KEYWORD = "class ";

    private static String compileMagmaToJava(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return JavaLang.renderJavaClass(namespace);
        } else if (magmaInput.equals(MagmaLang.renderMagmaDefinition())) {
            return JavaLang.renderJavaClass(namespace, Lang.renderBlock(JavaLang.renderJavaDefinition()));
        } else {
            throw createUnknownInput(magmaInput);
        }
    }

    private static CompileException createUnknownInput(String magmaInput) {
        return new CompileException("Unknown input: " + magmaInput);
    }

    private static String compileJavaToMagma(String javaInput) throws CompileException {
        if (javaInput.startsWith(CLASS_KEYWORD)) {
            var contentStart = javaInput.indexOf('{');
            var contentEnd = javaInput.lastIndexOf('}');
            var contentString = javaInput.substring(contentStart + 1, contentEnd).strip();
            var separator = contentString.indexOf(';');
            if (separator == -1) return "";

            var typeString = contentString.substring(0, separator).strip();
            if (typeString.isEmpty()) return "";
            else return MagmaLang.renderMagmaDefinition();
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
        var value = JavaLang.renderJavaClass(className);
        assertEquals(value, compileMagmaToJava(className, compileJavaToMagma(value)));
    }

    @Test
    void definition() throws CompileException {
        var value = JavaLang.renderJavaClass(TEST_NAME, Lang.renderBlock(JavaLang.renderJavaDefinition()));
        assertEquals(value, compileMagmaToJava(TEST_NAME, compileJavaToMagma(value)));
    }
}
