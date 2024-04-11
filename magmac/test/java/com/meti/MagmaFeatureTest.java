package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Application.*;
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

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertMagma(renderMagmaFunction(TEST_UPPER_SYMBOL, renderBlock(renderMagmaDefinition(name))));
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
