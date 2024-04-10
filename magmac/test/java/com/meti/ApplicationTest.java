package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.JavaLang.*;
import static com.meti.Lang.renderBlock;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final String TEST_UPPER_SYMBOL = "Test";

    private static void assertJava(String namespace, String content) {
        try {
            assertEquals(content, MagmaToJavaCompiler.compileMagmaToJava(namespace, JavaToMagmaCompiler.compileJavaToMagma(content)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> MagmaToJavaCompiler.compileMagmaToJava(TEST_UPPER_SYMBOL, "test"));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> JavaToMagmaCompiler.compileJavaToMagma(""));
    }

    @Test
    void magmaSmallest() throws CompileException {
        assertEquals("", JavaToMagmaCompiler.compileJavaToMagma(MagmaToJavaCompiler.compileMagmaToJava(TEST_UPPER_SYMBOL, "")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSmallest(String className) {
        assertJava(className, renderJavaClass(className));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaField(String name) {
        assertJava(TEST_UPPER_SYMBOL, renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(renderJavaDefinition(name))));
    }

    @Test
    void javaFieldType() {
        assertJava(TEST_UPPER_SYMBOL,
                renderJavaClass(TEST_UPPER_SYMBOL,
                        renderBlock(
                                renderJavaDefinition("test", JavaLang.LONG_TYPE)
                        )
                )
        );
    }
}
