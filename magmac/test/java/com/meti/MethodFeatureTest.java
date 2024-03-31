package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import com.meti.magma.MagmaDeclaration;
import com.meti.magma.MagmaMethodBuilder;
import com.meti.magma.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.FeatureTest.assertWithinClass;

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
                new MagmaDeclaration("", "", TEST_SYMBOL, "() => Void").render() + ";");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleMethod(String name) {
        assertWithinClass(renderJavaMethod("", name, "{}"),
                new MagmaMethodBuilder().withPrefix("").withName(name).withType("Void").withContent("{}").withExceptionString("").build().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testAnnotation(String name) {
        assertWithinClass(renderJavaMethod(new Annotation(name, "").renderAnnotation(), TEST_SYMBOL, "{}"),
                new MagmaMethodBuilder().withPrefix(new Annotation(name, "").renderAnnotation()).withName(TEST_SYMBOL).withType("Void").withContent("{}").withExceptionString("").build().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testException(String name) {
        assertWithinClass(renderJavaMethod("", "", TEST_SYMBOL, " throws " + name + " ", "{}"),
                new MagmaMethodBuilder().withPrefix("").withName(TEST_SYMBOL).withType("Void").withContent("{}").withExceptionString(" ? " + name).build().render());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleAnnotations(int count) {
        var rendered = new Annotation("First", "").renderAnnotation().repeat(count);
        assertWithinClass(renderJavaMethod(rendered, TEST_SYMBOL, "{}"),
                new MagmaMethodBuilder().withPrefix(rendered).withName(TEST_SYMBOL).withType("Void").withContent("{}").withExceptionString("").build().render());
    }

    @Test
    void testAnnotationWithValues() {
        assertWithinClass(renderJavaMethod(new Annotation("First", "(ints = {2, 3})").renderAnnotation(), TEST_SYMBOL, "{}"),
                new MagmaMethodBuilder().withPrefix(new Annotation("First", "(ints = [2, 3])").renderAnnotation()).withName(TEST_SYMBOL).withType("Void").withContent("{}").withExceptionString("").build().render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void testStatic(String className) {
        String content = new MagmaMethodBuilder().withPrefix("").withName(TEST_SYMBOL).withType("Void").withContent("{}").withExceptionString("").build().render();
        assertCompile(
                new JavaClassNodeBuilder().withPrefix("").withName(className).withContent(renderJavaMethod("", "static ", TEST_SYMBOL, "", "{}")).build().render(),
                new ObjectNode("", className, content).render()
                + "\n\n"
                + new MagmaClassNodeBuilder().withPrefix("").withName(className).withContent("").build().render());
    }
}
