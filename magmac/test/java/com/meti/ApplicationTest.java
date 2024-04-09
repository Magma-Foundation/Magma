package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String TEST_NAME = "Test";
    public static final String CLASS_KEYWORD = "class ";

    private static String renderBlock() {
        return renderBlock("");
    }

    private static String renderBlock(String fieldString) {
        return "{" + fieldString + "public static void main(String[] args){}}";
    }

    private static String renderJavaClass(String name) {
        return renderJavaClass(name, renderBlock());
    }

    private static String renderJavaClass(String name, String value) {
        return CLASS_KEYWORD + name + " " + value;
    }

    private static String compileMagmaToJava(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return renderJavaClass(namespace);
        } else if (magmaInput.equals(renderMagmaDefinition())) {
            return renderJavaClass(namespace, renderBlock(renderJavaDefinition()));
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
            else return renderMagmaDefinition();
        } else {
            throw createUnknownInput(javaInput);
        }
    }

    private static String renderMagmaDefinition() {
        return "let x = 0;";
    }

    private static String renderJavaDefinition() {
        return "int value = 0;";
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
        var value = renderJavaClass(className);
        assertEquals(value, compileMagmaToJava(className, compileJavaToMagma(value)));
    }

    @Test
    void definition() throws CompileException {
        var value = renderJavaClass(TEST_NAME, renderBlock(renderJavaDefinition()));
        assertEquals(value, compileMagmaToJava(TEST_NAME, compileJavaToMagma(value)));
    }
}
