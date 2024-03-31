package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.*;
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
                renderMagmaMethodWithType("", name, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testAnnotation(String name) {
        assertWithinClass(renderJavaMethod(renderAnnotation(name), TEST_SYMBOL),
                renderMagmaMethodWithType(renderAnnotation(name), TEST_SYMBOL, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testException(String name) {
        assertWithinClass(renderJavaMethod("", TEST_SYMBOL, " throws " + name + " "),
                renderMagmaMethodWithException("", TEST_SYMBOL, "Void", "{}", name));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleAnnotations(int count) {
        var rendered = renderAnnotation("First").repeat(count);
        assertWithinClass(renderJavaMethod(rendered, TEST_SYMBOL),
                renderMagmaMethodWithType(rendered, TEST_SYMBOL, "Void", "{}"));
    }

    @Test
    void testAnnotationWithValues() {
        assertWithinClass(renderJavaMethod(renderAnnotation("First", "(ints = {2, 3})"), TEST_SYMBOL),
                renderMagmaMethodWithType(renderAnnotation("First", "(ints = [2, 3])"), TEST_SYMBOL, "Void", "{}"));
    }
}
