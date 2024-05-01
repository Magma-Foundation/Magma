package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    private static void assertRun(String input, String output) {
        assertEquals(output, Compiler.run(input));
    }

    @Test
    void importChild() {
        assertRun(Compiler.JAVA_IMPORT + Compiler.renderJavaClass(Compiler.TEST_UPPER_SYMBOL),
                Compiler.MAGMA_IMPORT + Compiler.renderMagmaFunction(Compiler.TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void className(String name) {
        assertRun(Compiler.renderJavaClass(name), Compiler.renderMagmaFunction(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun("package " + name + ";" + Compiler.renderJavaClass(Compiler.TEST_UPPER_SYMBOL), Compiler.renderMagmaFunction(Compiler.TEST_UPPER_SYMBOL));
    }

    @Test
    void classPublic() {
        assertRun(Compiler.renderJavaClass(Compiler.TEST_UPPER_SYMBOL, Compiler.PUBLIC_KEYWORD_WITH_SPACE), Compiler.renderMagmaFunction(Compiler.TEST_UPPER_SYMBOL, Compiler.EXPORT_KEYWORD_WITH_SPACE));
    }
}