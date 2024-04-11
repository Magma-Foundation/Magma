package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {

    public static final String TEST_SYMBOL = "Main";
    public static final String CLASS_KEYWORD = "class ";
    public static final String JAVA_CLASS_BODY = " {}";
    public static final String DEF_KEYWORD = "def ";
    public static final String MAGMA_FUNCTION_BODY = "() => {}";

    private static String renderJavaClass(String name) {
        return CLASS_KEYWORD + name + JAVA_CLASS_BODY;
    }

    private static String renderMagmaFunction(String name) {
        return CLASS_KEYWORD + DEF_KEYWORD + name + MAGMA_FUNCTION_BODY;
    }

    private static String compileJavaToMagma(String input) throws CompileException {
        if (input.startsWith(CLASS_KEYWORD)) {
            var name = input.substring(CLASS_KEYWORD.length(), input.indexOf(JAVA_CLASS_BODY));
            return renderMagmaFunction(name);
        } else {
            throw createUnknownInputError(input);
        }
    }

    private static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    private static String compileMagmaToJava(String input) throws CompileException {
        if (input.startsWith(CLASS_KEYWORD + DEF_KEYWORD)) {
            var name = input.substring((CLASS_KEYWORD + DEF_KEYWORD).length(), input.indexOf(MAGMA_FUNCTION_BODY));
            return renderJavaClass(name);
        } else {
            throw createUnknownInputError(input);
        }
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(TEST_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void magmaSimple(String name) throws CompileException {
        assertEquals(renderMagmaFunction(name), compileJavaToMagma(compileMagmaToJava(renderMagmaFunction(name))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) throws CompileException {
        assertEquals(renderJavaClass(name), compileMagmaToJava(compileJavaToMagma(renderJavaClass(name))));
    }
}
