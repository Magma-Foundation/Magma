package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.assertCompile;
import static com.meti.JavaLang.renderJavaClass;
import static com.meti.MagmaLang.renderMagmaFunction;

public class ClassFeatureTest {

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimple(String name) {
        assertCompile(renderJavaClass(name), renderMagmaFunction(name));
    }
}
