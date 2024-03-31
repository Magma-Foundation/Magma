package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.Compiler.*;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.FeatureTest.assertWithinClass;
import static com.meti.JavaLang.renderJavaClass;

public class MethodFeatureTest {
    private static String renderJavaMethod(String prefix, String name, String content) {
        return renderJavaMethod(prefix, "", name, "", content);
    }

    private static String renderJavaMethod(String annotations, String flagsString, String name, String throwsString, String content) {
        return annotations + "\n" + flagsString + "void " + name + "()" + throwsString + content;
    }

    @Test
    void testWithNoBody() {
        assertWithinClass(renderJavaMethod("", TEST_SYMBOL, ";"),
                new MagmaDefinitionHeader("", "", TEST_SYMBOL, "() => Void").render() + ";");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleMethod(String name) {
        assertWithinClass(renderJavaMethod("", name, "{}"),
                renderMagmaMethodWithType("", name, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testAnnotation(String name) {
        assertWithinClass(renderJavaMethod(renderAnnotation(name), TEST_SYMBOL, "{}"),
                renderMagmaMethodWithType(renderAnnotation(name), TEST_SYMBOL, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testException(String name) {
        assertWithinClass(renderJavaMethod("", "", TEST_SYMBOL, " throws " + name + " ", "{}"),
                renderMagmaMethodWithException("", TEST_SYMBOL, "Void", "{}", name));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleAnnotations(int count) {
        var rendered = renderAnnotation("First").repeat(count);
        assertWithinClass(renderJavaMethod(rendered, TEST_SYMBOL, "{}"),
                renderMagmaMethodWithType(rendered, TEST_SYMBOL, "Void", "{}"));
    }

    @Test
    void testAnnotationWithValues() {
        assertWithinClass(renderJavaMethod(renderAnnotation("First", "(ints = {2, 3})"), TEST_SYMBOL, "{}"),
                renderMagmaMethodWithType(renderAnnotation("First", "(ints = [2, 3])"), TEST_SYMBOL, "Void", "{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void testStatic(String className) {
        assertCompile(
                renderJavaClass(className, renderJavaMethod("", "static ", TEST_SYMBOL, "", "{}")),
                renderObject(className, renderMagmaMethodWithType("", TEST_SYMBOL, "Void", "{}"))
                + "\n\n"
                + renderMagmaClass(className, ""));
    }
}
