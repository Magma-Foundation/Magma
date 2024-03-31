package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.*;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfaceFeatureTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleInterface(String name) {
        assertEquals(renderMagmaTrait(name), compile(renderJavaInterface(name)));
    }

    @Test
    void testPublic() {
        assertEquals(renderMagmaTrait(EXPORT_KEYWORD, TEST_SYMBOL, "{}"), compile(renderJavaInterface(PUBLIC_KEYWORD, TEST_SYMBOL)));
    }
}
