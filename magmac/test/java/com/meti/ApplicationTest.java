package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";

    private static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    private static String renderMagmaFunction(String modifierString, String name) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_CONTENT;
    }

    private static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    private static String renderJavaClass(String modifierString, String name) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + name + CLASS_CONTENT;
    }

    private static String compile(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var nameEnd = input.indexOf(CLASS_CONTENT);
        var name = input.substring(nameStart, nameEnd);

        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        return renderMagmaFunction(modifierString, name);
    }

    @Test
    void publicClass() {
        var input = renderJavaClass(PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL);
        var output = renderMagmaFunction(EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL);
        assertEquals(output, compile(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleClass(String name) {
        var input = renderJavaClass(name);
        var output = renderMagmaFunction(name);
        assertEquals(output, compile(input));
    }
}
