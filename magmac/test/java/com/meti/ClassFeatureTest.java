package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {

    public static final String TEST_NAME = "Test";

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleClasses(String name) {
        assertCompile(Compiler.renderJavaClass(name), Compiler.renderMagmaClass(name));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(Compiler.renderPublicJavaClass(TEST_NAME), Compiler.renderExportedMagmaClass(TEST_NAME));
    }
}
