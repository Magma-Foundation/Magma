package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_END = " {}";

    private static String renderMagmaFunction(String name) {
        return CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_END;
    }

    private static String run(String input) {
        if (input.startsWith(CLASS_KEYWORD_WITH_SPACE)) {
            var name = input.substring(CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_END));
            return renderMagmaFunction(name);
        } else {
            throw new UnsupportedOperationException("No class present.");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void className(String name) {
        assertEquals(renderMagmaFunction(name), run(CLASS_KEYWORD_WITH_SPACE + name + CLASS_END));
    }
}
