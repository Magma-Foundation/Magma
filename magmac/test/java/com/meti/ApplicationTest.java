package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.JavaLang.renderJavaClass;
import static com.meti.JavaLang.renderJavaDefinition;
import static com.meti.Lang.renderBlock;
import static com.meti.MagmaLang.LET_KEYWORD;
import static com.meti.MagmaLang.renderMagmaDefinition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String TEST_NAME = "Test";

    private static String compileMagmaToJava(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return renderJavaClass(namespace);
        }

        if (magmaInput.startsWith(LET_KEYWORD)) {
            var name = magmaInput.substring(LET_KEYWORD.length(), magmaInput.indexOf('=')).strip();
            return renderJavaClass(namespace, renderBlock(renderJavaDefinition(name)));
        }

        throw createUnknownInput(magmaInput);
    }

    private static CompileException createUnknownInput(String magmaInput) {
        return new CompileException("Unknown input: " + magmaInput);
    }

    private static String compileJavaToMagma(String javaInput) throws CompileException {
        if (javaInput.startsWith(JavaLang.CLASS_KEYWORD)) {
            var contentStart = javaInput.indexOf('{');
            var contentEnd = javaInput.lastIndexOf('}');
            var contentString = javaInput.substring(contentStart + 1, contentEnd).strip();
            var separator = contentString.indexOf(';');
            if (separator == -1) return "";

            var fieldString = contentString.substring(0, separator).strip();
            if (fieldString.isEmpty()) return "";

            var valueSeparator = fieldString.indexOf('=');
            var before = fieldString.substring(0, valueSeparator).strip();
            var nameSeparator = before.lastIndexOf(' ');
            var name = before.substring(nameSeparator + 1).strip();
            return renderMagmaDefinition(name);
        } else {
            throw createUnknownInput(javaInput);
        }
    }

    private static void assertJava(String namespace, String content) throws CompileException {
        assertEquals(content, compileMagmaToJava(namespace, compileJavaToMagma(content)));
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
    void magmaSmallest() throws CompileException {
        assertEquals("", compileJavaToMagma(compileMagmaToJava(TEST_NAME, "")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSmallest(String className) throws CompileException {
        assertJava(className, renderJavaClass(className));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaField(String name) throws CompileException {
        assertJava(TEST_NAME, renderJavaClass(TEST_NAME, renderBlock(renderJavaDefinition(name))));
    }
}
