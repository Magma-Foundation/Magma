package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.TEST_UPPER_SYMBOL;
import static com.meti.JavaLang.*;
import static com.meti.Lang.renderBlock;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSmallest(String className) {
        JavaFeatureTest.assertJava(className, renderJavaClass(className));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaField(String name) {
        JavaFeatureTest.assertJava(TEST_UPPER_SYMBOL, renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(renderJavaDefinition(name))));
    }

    @Test
    void javaFieldType() {
        JavaFeatureTest.assertJava(TEST_UPPER_SYMBOL,
                renderJavaClass(TEST_UPPER_SYMBOL,
                        renderBlock(
                                renderJavaDefinition("test", LONG_TYPE)
                        )
                )
        );
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> JavaToMagmaCompiler.compileJavaToMagma(""));
    }
}
