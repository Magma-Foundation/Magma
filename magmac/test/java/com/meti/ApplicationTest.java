package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";

    private static String renderMagmaFunction(String name) {
        return CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_CONTENT;
    }

    private static String renderJavaClass(String name) {
        return CLASS_KEYWORD_WITH_SPACE + name + CLASS_CONTENT;
    }

    private static String compile(String input) {
        var name = input.substring(CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_CONTENT));
        return renderMagmaFunction(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simple(String name) {
        var input = renderJavaClass(name);
        var output = renderMagmaFunction(name);
        assertEquals(output, compile(input));
    }
}
