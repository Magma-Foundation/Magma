package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Application.*;
import static com.meti.CompiledTest.TEST_LOWER_SYMBOL;
import static com.meti.CompiledTest.TEST_UPPER_SYMBOL;
import static org.junit.jupiter.api.Assertions.*;

public class MagmaFeatureTest {

    private static void assertMagma(String input) {
        try {
            assertEquals(input, compileJavaToMagma(compileMagmaToJava(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static void assertMagmaFunctionStatement(String definition) {
        assertMagma(renderMagmaFunction(TEST_UPPER_SYMBOL, renderBlock(definition)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertMagmaFunctionStatement(renderMagmaDefinition(name));
    }

    @Test
    void definitionType() {
        assertMagmaFunctionStatement(renderMagmaDefinition(TEST_LOWER_SYMBOL, I16));
    }

    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(CompiledTest.TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void magmaSimple(String name) {
        assertMagma(renderMagmaFunction(name));
    }

    @Test
    void magmaPublic() {
        assertMagma(EXPORT_KEYWORD + renderMagmaFunction(CompiledTest.TEST_UPPER_SYMBOL));
    }
}
