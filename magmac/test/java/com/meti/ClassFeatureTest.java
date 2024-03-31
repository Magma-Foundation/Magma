package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleClass(String name) {
        assertCompile(Compiler.renderJavaClass(name), Compiler.renderMagmaClass(name));
    }
}
