package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfaceFeatureTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleInterface(String name) {
        assertEquals(renderMagmaTrait(name), compile(renderJavaInterface(name)));
    }
}
