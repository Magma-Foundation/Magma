package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String CLASS_KEYWORD = "class ";
    public static final String EMPTY_CONTENT = "{}";

    private static String run(String input) throws CompileException {
        if (input.startsWith(CLASS_KEYWORD)) {
            var name = input.substring(CLASS_KEYWORD.length(), input.indexOf(EMPTY_CONTENT)).strip();
            return renderMagmaClass(name);
        }
        throw new CompileException("No class present.");
    }

    private static String renderMagmaClass(String name) {
        return CLASS_KEYWORD + "def " + name + "() => " + EMPTY_CONTENT;
    }

    @Test
    void empty() {
        assertThrows(CompileException.class, () -> run(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleClass(String name) throws CompileException {
        assertEquals(renderMagmaClass(name), run(CLASS_KEYWORD + name + " " + EMPTY_CONTENT));
    }
}
