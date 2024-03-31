package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
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
                new MagmaMethodBuilder().setPrefix("").setName(name).setType("Void").setContent("{}").setExceptionString("").createMagmaMethodNode().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testAnnotation(String name) {
        assertWithinClass(renderJavaMethod(new Annotation(name, "").renderAnnotation(), TEST_SYMBOL, "{}"),
                new MagmaMethodBuilder().setPrefix(new Annotation(name, "").renderAnnotation()).setName(TEST_SYMBOL).setType("Void").setContent("{}").setExceptionString("").createMagmaMethodNode().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testException(String name) {
        assertWithinClass(renderJavaMethod("", "", TEST_SYMBOL, " throws " + name + " ", "{}"),
                new MagmaMethodBuilder().setPrefix("").setName(TEST_SYMBOL).setType("Void").setContent("{}").setExceptionString(" ? " + name).createMagmaMethodNode().render());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleAnnotations(int count) {
        var rendered = new Annotation("First", "").renderAnnotation().repeat(count);
        assertWithinClass(renderJavaMethod(rendered, TEST_SYMBOL, "{}"),
                new MagmaMethodBuilder().setPrefix(rendered).setName(TEST_SYMBOL).setType("Void").setContent("{}").setExceptionString("").createMagmaMethodNode().render());
    }

    @Test
    void testAnnotationWithValues() {
        assertWithinClass(renderJavaMethod(new Annotation("First", "(ints = {2, 3})").renderAnnotation(), TEST_SYMBOL, "{}"),
                new MagmaMethodBuilder().setPrefix(new Annotation("First", "(ints = [2, 3])").renderAnnotation()).setName(TEST_SYMBOL).setType("Void").setContent("{}").setExceptionString("").createMagmaMethodNode().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void testStatic(String className) {
        String content = new MagmaMethodBuilder().setPrefix("").setName(TEST_SYMBOL).setType("Void").setContent("{}").setExceptionString("").createMagmaMethodNode().render();
        assertCompile(
                renderJavaClass(className, renderJavaMethod("", "static ", TEST_SYMBOL, "", "{}")),
                new ObjectNode("", className, content).renderObject()
                + "\n\n"
                + new MagmaClassNodeBuilder().setPrefix("").setName(className).setContent("").createMagmaClassNode().render());
    }
}
