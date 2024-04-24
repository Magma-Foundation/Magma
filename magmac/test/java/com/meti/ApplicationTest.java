package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";
    public static final String DEF_KEYWORD_WITH_SPACE = "def ";
    public static final String MAGMA_FUNCTION_PREFIX = CLASS_KEYWORD_WITH_SPACE + DEF_KEYWORD_WITH_SPACE;
    public static final String FUNCTION_SEPARATOR = "() =>";

    private static String renderMagmaFunction(String name) {
        return MAGMA_FUNCTION_PREFIX + name + FUNCTION_SEPARATOR + CLASS_CONTENT;
    }

    private static String run(String input, boolean isJava) throws CompileException {
        if (isJava) {
            if (input.startsWith(CLASS_KEYWORD_WITH_SPACE)) {
                var name = input.substring(CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_CONTENT));
                return renderMagmaFunction(name);
            }
        } else {
            if (input.startsWith(MAGMA_FUNCTION_PREFIX)) {
                var name = input.substring(MAGMA_FUNCTION_PREFIX.length(), input.indexOf(FUNCTION_SEPARATOR));
                return renderJavaClass(name);
            }
        }

        throw new CompileException("No class present.");
    }

    private static String renderJavaClass(String name) {
        return CLASS_KEYWORD_WITH_SPACE + name + CLASS_CONTENT;
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileMagmaToJava(String name) throws CompileException {
        assertEquals(renderJavaClass(name), run(renderMagmaFunction(name), false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileJavaToMagma(String name) throws CompileException {
        assertEquals(renderMagmaFunction(name), run(renderJavaClass(name), true));
    }
}