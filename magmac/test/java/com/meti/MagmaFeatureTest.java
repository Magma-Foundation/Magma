package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Application.compileMagmaToJava;
import static com.meti.CompiledTest.TEST_UPPER_SYMBOL;
import static com.meti.Lang.EXPORT_KEYWORD;
import static com.meti.Lang.renderMagmaFunction;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MagmaFeatureTest {
    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void magmaSimple(String name) {
        MagmaTest.assertMagma(renderMagmaFunction(name));
    }

    @Test
    void magmaPublic() {
        MagmaTest.assertMagma(EXPORT_KEYWORD + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }
}
