package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private static void assertRun(String input, String output) {
        assertEquals(output, run(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChild(String name) {
        assertRun(renderJavaImport(name) + renderJavaClass(TEST_UPPER_SYMBOL),
                renderMagmaImport(name) + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(renderJavaClass(name), renderMagmaFunction(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun("package " + name + ";" + renderJavaClass(TEST_UPPER_SYMBOL), renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(renderJavaClass(TEST_UPPER_SYMBOL, PUBLIC_KEYWORD_WITH_SPACE), renderMagmaFunction(TEST_UPPER_SYMBOL, EXPORT_KEYWORD_WITH_SPACE));
    }
}