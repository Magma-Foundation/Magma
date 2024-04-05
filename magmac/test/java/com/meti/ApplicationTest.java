package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String CLASS_KEYWORD = "class ";
    public static final String EMPTY_CONTENT = "{}";
    public static final String TEST_NAME = "First";

    private static String run(String input) throws CompileException {
        var first = input.indexOf(CLASS_KEYWORD);
        if (first != -1) {
            var after = input.indexOf(CLASS_KEYWORD, first + CLASS_KEYWORD.length());
            if (after != -1) {
                throw new CompileException("Multiple classes are not permitted.");
            }

            var name = input.substring(CLASS_KEYWORD.length(), input.indexOf(EMPTY_CONTENT)).strip();
            return renderMagmaClass(name);
        }
        throw new CompileException("No class present.");
    }

    private static String renderMagmaClass(String name) {
        return CLASS_KEYWORD + "def " + name + "() => " + EMPTY_CONTENT;
    }

    private static String renderJavaClass(String name) {
        return CLASS_KEYWORD + name + " " + EMPTY_CONTENT;
    }

    @Test
    void empty() {
        assertThrows(CompileException.class, () -> run(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_NAME, "Second"})
    void simpleClass(String name) throws CompileException {
        assertEquals(renderMagmaClass(name), run(renderJavaClass(name)));
    }

    @Test
    void multipleClass() {
        assertThrows(CompileException.class, () -> run(renderJavaClass(TEST_NAME) + renderJavaClass(TEST_NAME)));
    }
}
