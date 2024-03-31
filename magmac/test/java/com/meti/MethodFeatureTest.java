package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.FeatureTest.assertWithinClass;

public class MethodFeatureTest {
    private static String renderJavaMethod(String prefix, String name) {
        return renderJavaMethod(prefix, name, "");
    }

    private static String renderJavaMethod(String prefix, String name, String throwsString) {
        return prefix + "void " + name + "()" + throwsString + "{}";
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleMethod(String name) {
        assertWithinClass(renderJavaMethod("", name),
                Compiler.renderMagmaMethodWithType("", name, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testAnnotation(String name) {
        assertWithinClass(renderJavaMethod(Compiler.renderAnnotation(name), TEST_SYMBOL),
                Compiler.renderMagmaMethodWithType(Compiler.renderAnnotation(name), TEST_SYMBOL, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testException(String name) {
        assertWithinClass(renderJavaMethod("", TEST_SYMBOL, " throws " + name + " "),
                Compiler.renderMagmaMethodWithException("", TEST_SYMBOL, "Void", "{}", name));
    }
}
