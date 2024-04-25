package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_END = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";

    private static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    private static String renderMagmaFunction(String modifiersString, String name) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_END;
    }

    private static String run(String input) {
        var classIndex = input.indexOf(CLASS_KEYWORD_WITH_SPACE);
        if (classIndex == -1) throw new UnsupportedOperationException("No class present.");

        var name = input.substring(classIndex + CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_END));
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        return renderMagmaFunction(modifierString, name);
    }

    private static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    private static String renderJavaClass(String modifiersString, String name) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + name + CLASS_END;
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertEquals(renderMagmaFunction(TEST_UPPER_SYMBOL), run("package " + name + ";" + renderJavaClass(TEST_UPPER_SYMBOL)));
    }

    @Test
    void classPublic() {
        assertEquals(renderMagmaFunction(EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), run(renderJavaClass(PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void className(String name) {
        assertEquals(renderMagmaFunction(name), run(renderJavaClass(name)));
    }
}
