package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final String TEST_UPPER_SYMBOL = "Main";

    private static void assertMagma(String input) {
        try {
            assertEquals(input, Application.compileJavaToMagma(Application.compileMagmaToJava(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static void assertJava(String input) {
        try {
            assertEquals(input, Application.compileMagmaToJava(Application.compileJavaToMagma(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> Application.compileJavaToMagma(""));
    }

    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> Application.compileMagmaToJava(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void magmaSimple(String name) {
        assertMagma(Application.renderMagmaFunction(name));
    }

    @Test
    void magmaPublic() {
        assertMagma(Application.EXPORT_KEYWORD + Application.renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        assertJava(Application.renderJavaClass(name));
    }

    @Test
    void javaPublic() {
        assertJava(Application.PUBLIC_KEYWORD + Application.renderJavaClass(TEST_UPPER_SYMBOL));
    }
}
